package com.turan.config;

import com.turan.exception.AuthEntryPoint;
import com.turan.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;


         private static final String REGISTER = "/register";
         private static final String AUTHENTICATE = "/authenticate";
         private static final String ADMIN = "/admin";




          public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                 http.csrf().disable()
                         .authorizeHttpRequests(request
                                 ->request.requestMatchers(REGISTER,AUTHENTICATE)
                                 .permitAll()
                                 .requestMatchers(ADMIN).hasRole("ADMIN")
                                 .requestMatchers(HttpMethod.GET,"/api/books/**").permitAll()
                                 .requestMatchers(HttpMethod.GET,"/api/users").hasRole("ADMIN")
                                 .requestMatchers(HttpMethod.POST).hasRole("ADMIN")
                                 .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
                                 .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                                 .anyRequest()
                                 .authenticated())
                         .sessionManagement(session
                                 ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                         .authenticationProvider(authenticationProvider)
                         .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
                         .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and();

                 return http.build();
          }

}
