package com.swissroute.backend.service;

import com.swissroute.backend.dto.response.StationResponse;
import com.swissroute.backend.repository.StationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {
    private final StationRepository repo;

    public StationService(StationRepository repo) {
        this.repo = repo;
    }

    public List<StationResponse> searchByCoordinates(Double x, Double y, String query){
        if (x != null || y != null) {
            // TODO: reemplazar con StationApiClient cuando se mergee HU-04 a develop
            // stationApiClient.searchByCoordinates(x, y)
            throw new UnsupportedOperationException("Pending StationApiClient integration (HU-04)");
        } else if (query != null) {
            throw new UnsupportedOperationException("Pending StationApiClient integration (HU-04)");
        } else {
            throw new IllegalArgumentException("Provide coordinates or a search query");
        }

    }


}
