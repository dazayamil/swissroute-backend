package com.swissroute.backend.dto.response;

import com.swissroute.backend.enums.TransportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteRouteResponse {

    private Long id;
    private String name;
    private String origin;
    private String destination;
    private TransportType transportType;
}
