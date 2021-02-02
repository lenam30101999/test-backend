package com.aibles.authenticationservice.service;

import dtos.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDTO register(SignUpRequestDTO signUpRequest);

    LoginResponseDTO login(LoginRequestDTO loginRequest);

    UserDetails loadUserByUsername(String username);

    UserDetails loadUserById(int id);
}
