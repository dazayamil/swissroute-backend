package com.swissroute.backend.service;

import com.swissroute.backend.dto.UserRegisterRequestDTO;
import com.swissroute.backend.dto.UserResponseDTO;
import com.swissroute.backend.entity.UserEntity;
import com.swissroute.backend.repository.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class UserEntityServiceTest {

    @Mock
    private UserEntityRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserEntityService service;

    @Test
    void registerUser_success(){

        UserRegisterRequestDTO request = new UserRegisterRequestDTO();

        request.setName("Ana");
        request.setEmail("Ana@gmail.com");
        request.setPassword("123456");
        request.setCityBase("Sevilla");

        when(repository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        when(passwordEncoder.encode(request.getPassword()))
                .thenReturn("PASSWORD_ENCRIPTADA");

        UserEntity savedUser = new UserEntity();

        savedUser.setId(1L);
        savedUser.setName(request.getName());
        savedUser.setEmail(request.getEmail());
        savedUser.setPassword("PASSWORD_ENCRIPTADA");
        savedUser.setCityBase(request.getCityBase());
        savedUser.setCreatedAt(LocalDateTime.now());

        when(repository.save(any(UserEntity.class)))
                .thenReturn(savedUser);

        UserResponseDTO response = service.registerUser(request);

        assertNotNull(response);

        assertEquals("Ana", response.getName());

        assertEquals("Sevilla", response.getCityBase());

        verify(repository).save(any(UserEntity.class));
    }
}
