package com.swissroute.backend.service;

import com.swissroute.backend.dto.UserRegisterRequestDTO;
import com.swissroute.backend.dto.UserResponseDTO;
import com.swissroute.backend.entity.UserEntity;
import com.swissroute.backend.exception.UserAlreadyExitsException;
import com.swissroute.backend.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserEntityService {

    private final UserEntityRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO registerUser(UserRegisterRequestDTO request){

        validateUser(request);

        UserEntity user = new UserEntity();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setCityBase(request.getCityBase());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setCreatedAt(LocalDateTime.now());

        UserEntity savedUser = repository.save(user);

        UserResponseDTO response = new UserResponseDTO();

        response.setId(savedUser.getId().toString());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setCityBase(savedUser.getCityBase());
        response.setCreatedAt(savedUser.getCreatedAt());

        return response;
    }

    public void validateUser(UserRegisterRequestDTO request){
        if(repository.existsByEmail(request.getEmail())){
            throw new UserAlreadyExitsException();
        }
    }
}
