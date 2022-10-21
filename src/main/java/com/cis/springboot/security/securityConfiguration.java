package com.cis.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class securityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthFilter authFilter(){
        return new AuthFilter();
    }
    private final String [] publicEndPoints={
            "/api/v1/auth/**",
    };


    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * first line for disabling cookies
     * second line cuz i will use jwt
     * third line is to select the endpoints that can be accessed by anyone "public"
     * forth endpoint that can be accessed without auth
     * fifth for requesting an auth for any other endpoints
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(publicEndPoints).permitAll()
                .anyRequest().authenticated()
                .and().addFilterAfter(authFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
