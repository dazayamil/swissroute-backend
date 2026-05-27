package com.swissroute.backend.controller;

import com.swissroute.backend.dto.UserRegisterRequestDTO;
import com.swissroute.backend.dto.UserResponseDTO;
import com.swissroute.backend.service.UserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "Registro de usuario")
public class UserEntityController {

    private final UserEntityService service;

    public UserEntityController(UserEntityService service){
        this.service = service;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario en el sistema")
    @ApiResponse(responseCode = "409", description = "El email ya existe")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRegisterRequestDTO request){

        UserResponseDTO response = service.registerUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
