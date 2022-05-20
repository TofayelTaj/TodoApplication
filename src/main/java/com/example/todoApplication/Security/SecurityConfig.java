package com.example.todoApplication.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private PasswordConfig passwordConfig;

    @Autowired
    private AuthenticationSuccessHandler LoginSuccessHandler;

    @Bean
    public UserDetailsService getUserDeailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public UserDetailsService getSupervisorDeatailsService(){
        return new SupervisorDetailsServiceImpl();
    }


    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.getUserDeailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordConfig.passwordEncoder());
        return daoAuthenticationProvider;
    }


    public DaoAuthenticationProvider supervisorDaoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(this.passwordConfig.passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.getSupervisorDeatailsService());
        return daoAuthenticationProvider;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
               .authorizeRequests()
                .antMatchers("/", "/user/signup", "/supervisor/signup" )
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .successHandler(LoginSuccessHandler)
               ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(this.getDaoAuthenticationProvider())
                .authenticationProvider(this.supervisorDaoAuthenticationProvider());
    }
}
