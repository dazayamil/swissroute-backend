package com.swissroute.backend.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StationResponse {
    private String id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Double distance; // metros al punto original
}
