package com.swissroute.backend.controller;

import com.swissroute.backend.dto.request.LoginRequest;
import com.swissroute.backend.dto.request.RegisterRequest;
import com.swissroute.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin("*")
@Tag(
        name = "Authentication",
        description = "Authentication endpoints"
)
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        authService.registrar(registerRequest);

        return ResponseEntity.ok(Map.of("mensaje", "Usuario registrado correctamente"));
    }

    @Operation(summary = "Authenticate user")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest);
        return ResponseEntity.ok(Map.of("token", token));
    }

    // Endpoint de prueba para ver si efectivamente una vez que el usuario ya está logueado puede llamar a otro endpoint
    // Recordar copiar el token que te dio el login y en postman ponerlo en Authorization -> bearer token, si no te da un
    // 403 Forbidden
    @Operation(summary = "Get authenticated user")
    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        return ResponseEntity.ok(Map.of("usuario", authentication.getName()));
    }


}
