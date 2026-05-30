package com.swissroute.backend.service;

import com.swissroute.backend.client.SwissApiClient;
import com.swissroute.backend.dto.response.ExternalStationboardResponseDTO;
import com.swissroute.backend.dto.response.StationboardDTO;
import com.swissroute.backend.entity.FavoriteStation;
import com.swissroute.backend.repository.FavoriteStationRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class StationboardService {

    private final SwissApiClient swissApiClient;
    private final FavoriteStationRepository favoriteStationRepository;

    public StationboardService(SwissApiClient swissApiClient,
                               FavoriteStationRepository favoriteStationRepository) {
        this.swissApiClient = swissApiClient;
        this.favoriteStationRepository = favoriteStationRepository;
    }

    public List<StationboardDTO> getStationboard(String station, Integer limit, String type) {
        StringBuilder uri = new StringBuilder("/stationboard?station=").append(station);

        if (limit != null) {
            uri.append("&limit=").append(limit);
        }
        if (type != null && !type.isBlank()) {
            uri.append("&transportations[]=").append(type);
        }

        ExternalStationboardResponseDTO response = swissApiClient.get(
                uri.toString(),
                new ParameterizedTypeReference<ExternalStationboardResponseDTO>() {}
        );

        return response.getStationboard().stream()
                .map(entry -> new StationboardDTO(
                        entry.getName(),
                        entry.getCategory(),
                        entry.getTo(),
                        entry.getStop().getDeparture()
                ))
                .collect(Collectors.toList());
    }

    public List<StationboardDTO> getStationboardByFavorite(Long favoriteId, Integer limit, String type) {
        FavoriteStation favorite = favoriteStationRepository.findById(favoriteId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Estación favorita no encontrada con id: " + favoriteId));

        return getStationboard(favorite.getStationName(), limit, type);
    }
}
