package com.swissroute.backend.dto.request;

import com.swissroute.backend.enums.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String city;
    private Role role = Role.USER;
}


