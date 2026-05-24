package com.swissroute.backend.service;

import com.swissroute.backend.config.JwtUtil;
import com.swissroute.backend.dto.request.LoginRequest;
import com.swissroute.backend.dto.request.RegisterRequest;
import com.swissroute.backend.entity.User;
import com.swissroute.backend.enums.Role;
import com.swissroute.backend.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioRepository repo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public User registrar(RegisterRequest registerRequest) {
        if (repo.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }
        String hash = passwordEncoder.encode(registerRequest.getPassword());
        Role role = Role.valueOf(registerRequest.getRole().toString().toUpperCase());
        User u = new User();
        u.setName(registerRequest.getName());
        u.setEmail(registerRequest.getEmail());
        u.setRole(Role.USER);
        u.setCity(registerRequest.getCity());
        u.setPasswordHash(hash);

        return repo.save(u);
    }

    public String login(LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        return jwtUtil.generateToken(auth);
    }
}
