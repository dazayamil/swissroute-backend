package com.swissroute.backend.controller;

import com.swissroute.backend.dto.request.LoginRequest;
import com.swissroute.backend.dto.request.RegisterRequest;
import com.swissroute.backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        authService.registrar(registerRequest);

        return ResponseEntity.ok(Map.of("mensaje", "Usuario registrado correctamente"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest);
        return ResponseEntity.ok(Map.of("token", token));
    }

    // Endpoint de prueba para ver si efectivamente una vez que el usuario ya está logueado puede llamar a otro endpoint
    // Recordar copiar el token que te dio el login y en postman ponerlo en Authorization -> bearer token, si no te da un
    // 403 Forbidden
    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        return ResponseEntity.ok(Map.of("usuario", authentication.getName()));
    }


}
