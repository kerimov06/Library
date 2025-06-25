package com.turan.config;

import com.turan.entity.Role;
import com.turan.entity.User;
import com.turan.repository.RoleRepository;
import com.turan.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

import static ch.qos.logback.core.joran.spi.ConsoleTarget.findByName;

@Component
public class AdminSetupConfig {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;



     public AdminSetupConfig
             (UserRepository userRepository,RoleRepository roleRepository,BCryptPasswordEncoder passwordEncoder){
                this.userRepository=userRepository;
                this.roleRepository=roleRepository;
                this.passwordEncoder=passwordEncoder;
     }
      @PostConstruct
      @Transactional
      public void init(){

                      Role roleAdmin =  roleRepository.findByName("ROLE_ADMIN")
                              .orElseGet(() -> {
                                     Role role = new Role();
                                      role.setName("ROLE_ADMIN");
                                      return roleRepository.save(role);
                              });



                      if (!userRepository.existsByUsername("admin")){
                             User  user = new User();
                             user.setUsername("admin");
                             user.setPassword(passwordEncoder.encode("admin421"));
                             user.setRoles(Set.of(roleAdmin));
                             user.setName("Admin");
                             userRepository.save(user);
                          System.out.println("Admin istifadecisi yaradildi");
                      }

      }


}
