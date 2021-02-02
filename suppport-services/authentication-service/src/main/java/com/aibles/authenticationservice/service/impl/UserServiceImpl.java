package com.aibles.authenticationservice.service.impl;

import com.aibles.authenticationservice.entity.CustomUserDetails;
import com.aibles.authenticationservice.entity.User;
import com.aibles.authenticationservice.entity.types.UserRole;
import com.aibles.authenticationservice.service.BaseService;
import com.aibles.authenticationservice.service.UserService;
import com.aibles.utils.util.Helpers;
import dtos.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Log4j2
@Service
@Transactional
public class UserServiceImpl extends BaseService implements UserService, UserDetailsService {

    @Override
    public UserDTO register(SignUpRequestDTO signUpRequest){
        if (checkUsernameExists(signUpRequest.getUsername())){
            User user = User.builder()
                    .uid(Helpers.generateUid())
                    .username(signUpRequest.getUsername())
                    .email(signUpRequest.getEmail())
                    .phoneNumber(signUpRequest.getPhoneNumber())
                    .password(encodePassword(signUpRequest))
                    .userRole(UserRole.ROLE_USER)
                    .fullName(signUpRequest.getFullName())
                    .build();

            user = userRepository.save(user);
            return modelMapper.convertUserToDTO(user);
        }else return null;
    }

    @Override
    @CachePut(value = "tokenCache", key = "#loginRequest.username")
    public LoginResponseDTO login(LoginRequestDTO loginRequest){
        if (!checkUsernameExists(loginRequest.getUsername())){
            User user = findUserByUsername(loginRequest.getUsername());
            return getLoginResponse(loginRequest, user);
        }else return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findUserByUsernameOrPhoneNumberOrEmail(username, username, username);
        return CustomUserDetails.create(user);
    }

    @Override
    public UserDetails loadUserById(int id){
        User user = userRepository.findById(id);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("Not found");
        }
        return CustomUserDetails.create(user);
    }

    private String encodePassword(SignUpRequestDTO signUpRequest){
        return passwordEncoder.encode(signUpRequest.getPassword());
    }

    private boolean checkUsernameExists(String username){
        User user = findUserByUsername(username);
        return Objects.isNull(user);
    }

    private User findUserByUsername(String username) {
        User user;
        if (Helpers.regexPhoneNumber(username)) {
            user = userRepository.findUserByPhoneNumber(username);
        } else if (Helpers.regexEmail(username)) {
            String email = username.toLowerCase();
            user = userRepository.findUserByEmail(email);
        } else {
            user = userRepository.findUserByUsername(username);
        }
        return user;
    }

    private LoginResponseDTO getLoginResponse(LoginRequestDTO dto, User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getUsername(),
                            dto.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = tokenProvider.generateAccessToken(user);
            String refreshToken = tokenProvider.generateRefreshToken(user);
            return new LoginResponseDTO(accessToken, refreshToken);
        }catch (Exception e){
            log.debug(e);
        }
        return null;
    }
}

