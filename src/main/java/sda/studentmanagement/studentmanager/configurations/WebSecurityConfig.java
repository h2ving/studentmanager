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

import static org.springframework.http.HttpMethod.*;


// OLD VERSION
/*
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;


 //!!!!! DELETE We don't log out in server side, client just destroys the token in session storage and the we can't make any requests
    @Autowired
    CustomLogoutHandler customLogoutHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //!!!!!! DELETE? because we have JWT authentication -> auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT email, password, true from user where email=?") //TODO: add a enabled flag for an user
                .authoritiesByUsernameQuery("SELECT email, role from user where email=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/user/**").permitAll()
//                .antMatchers("/admin/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic()
//                .and()
//                .formLogin().loginPage("/user/login")
//                .loginProcessingUrl("/user/login")
//                .defaultSuccessUrl("/", true)
//                .failureHandler(authenticationFailureHandler())
//                .and()
//                .logout()
//                .logoutUrl("/user/logout")
//                .addLogoutHandler(customLogoutHandler);



        //!!!!! DELETE, new rules with JWT are down

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/user/**").hasAnyAuthority("STUDENT", "PROFESSOR")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }





    //!!!!! BCrypt is already used when signing in DELETE
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //!!!!! Handled with roles already -> .hasAnyAuthority("Student", "Professor", "Admin"); DELETE
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailure();
    }
}
*/
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
        //USER ROUTES
        http.authorizeRequests().antMatchers(GET, "/api/users/**").hasAnyAuthority("Professor", "Admin");
        http.authorizeRequests().antMatchers(POST, "/api/user/save/**", "/api/role/**").hasAuthority("Admin");
        http.authorizeRequests().antMatchers(GET, "/api/user/**").hasAnyAuthority("Student", "Professor", "Admin");

        //SESSION ROUTES
        http.authorizeRequests().antMatchers(GET, "/api/sessions/**").hasAnyAuthority("Student", "Professor", "Admin");
        http.authorizeRequests().antMatchers(POST, "/api/session/**").hasAnyAuthority("Professor", "Admin");
        http.authorizeRequests().antMatchers(DELETE, "/api/session/delete/**").hasAnyAuthority("Admin");

        //COURSE ROUTES


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
