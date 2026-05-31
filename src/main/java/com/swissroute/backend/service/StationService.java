package com.swissroute.backend.service;

import com.swissroute.backend.client.SwissApiClient;
import com.swissroute.backend.dto.response.StationResponse;
import com.swissroute.backend.dto.response.StationsApiResponse;
import com.swissroute.backend.exception.ExternalApiClientException;
import com.swissroute.backend.exception.ExternalApiServerException;
import com.swissroute.backend.exception.ExternalApiUnavailableException;
import com.swissroute.backend.exception.QueryParameterRequiredException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class StationService {
    private final SwissApiClient swissApiClient;

    public StationService(SwissApiClient swissApiClient) {
        this.swissApiClient = swissApiClient;
    }

    public List<StationResponse> searchByName(String query) {
        if (query == null || query.isBlank()) {
            throw new QueryParameterRequiredException("The query parameter is required");
        }

        String uri = UriComponentsBuilder.fromPath("/locations")
                .queryParam("query", query.trim())
                .build()
                .encode()
                .toUriString();

        StationsApiResponse apiResponse;
        try {
            apiResponse = swissApiClient.get(uri, new ParameterizedTypeReference<>() {});
        } catch (ExternalApiClientException | ExternalApiServerException | WebClientException ex) {
            throw new ExternalApiUnavailableException(
                    "The external transport API is currently unavailable", ex);
        }

        return mapStations(apiResponse);
    }

    public List<StationResponse> searchByCoordinates(Double x, Double y, String query) {
        String uri;
        if (query != null && !query.isBlank()) { //si query está vacia busca por coords
            uri = "/locations?query=" + query;
        } else if (x != null && y != null) {
            uri = "/locations?x=" + x + "&y=" + y;
        } else {
            throw new IllegalArgumentException("Provide coordinates (x, y) or a search query");
        }

        StationsApiResponse apiResponse = swissApiClient.get(
                uri,
                new ParameterizedTypeReference<>() {}
        );

        return mapStations(apiResponse);
    }

    private List<StationResponse> mapStations(StationsApiResponse apiResponse) {
        if (apiResponse == null || apiResponse.getStations() == null) {
            return List.of();
        }

        return apiResponse.getStations().stream()
                .map(s -> StationResponse.builder()
                        .id(s.getId())
                        .name(s.getName())
                        .latitude(s.getCoordinate() != null ? s.getCoordinate().getX() : null)
                        .longitude(s.getCoordinate() != null ? s.getCoordinate().getY() : null)
                        .distance(s.getDistance() != null ? s.getDistance().doubleValue() : null)
                        .build())
                .toList();
    }
}
