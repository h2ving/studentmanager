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
import sda.studentmanagement.studentmanager.filters.CustomAuthenticationFilter;
import sda.studentmanagement.studentmanager.filters.CustomAuthorizationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;


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

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh/**").permitAll();

        http.authorizeRequests().antMatchers(GET, "/api/user/**").hasAnyAuthority("Student", "Professor", "Admin");
        http.authorizeRequests().antMatchers(GET, "/api/users/**").hasAnyAuthority("Professor", "Admin");
        http.authorizeRequests().antMatchers(POST, "/api/user/save/**", "/api/role/**").hasAuthority("Admin");
        http.authorizeRequests().antMatchers(POST, "/api/user/spawn/**", "/api/spawnmany/**").hasAuthority("Admin");

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
