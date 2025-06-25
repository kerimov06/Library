package com.turan.config;

import com.turan.entity.User;
import com.turan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class AppConfig {

    @Autowired
    private UserRepository userRepository;



    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                Set<GrantedAuthority> authorities = user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toSet());

                return new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        authorities
                );
            }

        };


    }

            @Bean
        public AuthenticationProvider authenticationProvider(){
            DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
              daoAuthenticationProvider.setPasswordEncoder(encoder());
              daoAuthenticationProvider.setUserDetailsService(userDetailsService());

              return daoAuthenticationProvider;
        }

        @Bean
        public BCryptPasswordEncoder encoder(){
             return new BCryptPasswordEncoder();
        }

}
