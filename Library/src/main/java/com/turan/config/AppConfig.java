package com.turan.config;

import com.turan.entity.User;
import com.turan.repository.UserRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Configuration
public class AppConfig {

    @Autowired
    private UserRepository userRepository;



       @Bean
       public UserDetailsService userDetailsService(){
            return new UserDetailsService() {
                @Override
                public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                    Optional<User> optional = userRepository.findByUsername(username);
                    if (optional.isPresent()) {
                        return optional.get();
                    }
                    return null;
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
