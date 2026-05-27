package com.swissroute.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDTO {

    private String id;
    private String name;
    private String email;
    private String cityBase;
    private LocalDateTime createdAt;

}
