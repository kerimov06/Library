package com.turan.service.impl;


import com.turan.dto.DtoUser;
import com.turan.entity.AuthRequest;
import com.turan.entity.AuthResponse;
import com.turan.entity.User;
import com.turan.repository.RoleRepository;
import com.turan.repository.UserRepository;
import com.turan.service.IAuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public DtoUser register(AuthRequest request) {
        DtoUser dtoUser = new DtoUser();

        User user = new User();
         user.setUsername(request.getUsername());
         user.setPassword(request.getPassword());

          User saveUser = userRepository.save(user);
        BeanUtils.copyProperties(saveUser,dtoUser);
        return dtoUser;
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {


        return null;
    }
}
