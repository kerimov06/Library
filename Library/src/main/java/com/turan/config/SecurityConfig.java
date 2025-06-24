package com.turan.config;

import com.turan.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


         private static final String REGISTER = "/register";
         private static final String AUTHENTICATE = "/authenticate";



          public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                 http.csrf().disable()
                         .authorizeHttpRequests(request
                                 ->request.requestMatchers(REGISTER,AUTHENTICATE)
                                 .permitAll()
                                 .anyRequest()
                                 .authenticated())
                         .sessionManagement(session
                                 ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                         .authenticationProvider(authenticationProvider)
                         .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);

                 return http.build();
          }





}
