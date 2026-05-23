package com.swissroute.backend.dto.request;

import com.swissroute.backend.enums.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String nombre;
    private String email;
    private String password;
    private String ciudad;
    private Role role = Role.USER;
}


