package com.swissroute.backend.dto.response;

import java.time.LocalDateTime;

public record FavoriteStationResponse(
        Long id,
        String externalStationId,
        String stationName,
        LocalDateTime createdAt
) {}
