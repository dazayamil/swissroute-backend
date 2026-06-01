package com.swissroute.backend.dto.request;

import com.swissroute.backend.enums.TransportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateFavoriteRouteRequest {

    private String name;
    private String origin;
    private String destination;
    private TransportType transportType;
}
