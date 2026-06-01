package com.swissroute.backend.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StationDTO {
    private String id;
    private String nombre;
    private Double latitud;
    private Double longitud;
}
