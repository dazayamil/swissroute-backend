package com.swissroute.backend.dto.response;

public record StationboardDTO(
        String serviceName,
        String category,
        String finalDestination,
        String departureTime
) {}
