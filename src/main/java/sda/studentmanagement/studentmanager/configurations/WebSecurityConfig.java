package sda.studentmanagement.studentmanager.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import sda.studentmanagement.studentmanager.filters.CustomAuthenticationFilter;
import sda.studentmanagement.studentmanager.filters.CustomAuthorizationFilter;

import static org.springframework.http.HttpMethod.*;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh/**").permitAll();
        //USER ROUTES
        http.authorizeRequests().antMatchers(GET, "/api/users/**").hasAnyAuthority("Professor", "Admin");
        http.authorizeRequests().antMatchers(POST, "/api/user/save/**", "/api/role/**").hasAuthority("Admin");
        http.authorizeRequests().antMatchers(GET, "/api/user/**").hasAnyAuthority("Student", "Professor", "Admin");
        http.authorizeRequests().antMatchers(POST, "/api/spawn/**", "/api/spawnmany/**").hasAuthority("Admin");

        //SESSION ROUTES
        http.authorizeRequests().antMatchers(GET, "/api/sessions/**").hasAnyAuthority("Student", "Professor", "Admin");
        http.authorizeRequests().antMatchers(POST, "/api/session/**").hasAnyAuthority("Professor", "Admin");
        http.authorizeRequests().antMatchers(DELETE, "/api/session/delete/**").hasAuthority("Admin");

        //COURSE ROUTES
        http.authorizeRequests().antMatchers(GET, "/api/course/**").hasAnyAuthority("Student", "Professor", "Admin");
        http.authorizeRequests().antMatchers(GET, "/api/courses/**").hasAnyAuthority("Student", "Professor", "Admin");
        http.authorizeRequests().antMatchers(POST, "/api/course/save").hasAnyAuthority("Professor", "Admin");
        http.authorizeRequests().antMatchers(POST, "/api/course/spawn").hasAuthority("Admin");
        http.authorizeRequests().antMatchers(POST, "/api/course/spawn/**").hasAuthority("Admin");

        //GRADE ROUTES
        http.authorizeRequests().antMatchers(GET, "/api/grades/**").hasAnyAuthority("Student", "Professor", "Admin");
        http.authorizeRequests().antMatchers(POST, "/api/grade/**").hasAnyAuthority("Professor", "Admin");
        http.authorizeRequests().antMatchers(GET, "/api/grade/**").hasAnyAuthority("Student", "Professor", "Admin");

        //ATTENDANCE ROUTES
        http.authorizeRequests().antMatchers(GET, "/api/attendances/**").hasAnyAuthority("Student", "Professor", "Admin");
        http.authorizeRequests().antMatchers(POST, "/api/attendances/save").hasAnyAuthority("Professor", "Admin");
        http.authorizeRequests().antMatchers(GET, "/api/attendances").hasAnyAuthority("Professor", "Admin");


        http.authorizeRequests().anyRequest().authenticated();

        // Filter that checks the User every time they log in
        // This also gives us /login page by default without having to make it in controller
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
