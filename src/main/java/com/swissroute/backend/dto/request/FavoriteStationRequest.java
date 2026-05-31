package com.swissroute.backend.dto.request;

public record FavoriteStationRequest(
        String externalStationId,
        String stationName
) {}
