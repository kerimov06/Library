package com.turan.service;

import com.turan.dto.DtoUser;
import com.turan.entity.AuthRequest;
import com.turan.entity.AuthResponse;

public interface IAuthService {

      public DtoUser register(AuthRequest request);
      public AuthResponse authenticate(AuthRequest request);
}
