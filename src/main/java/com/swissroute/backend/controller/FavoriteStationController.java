package com.swissroute.backend.controller;

import com.swissroute.backend.dto.response.StationboardDTO;
import com.swissroute.backend.service.StationboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estaciones-favoritas")
public class FavoriteStationController {

    private final StationboardService stationboardService;

    public FavoriteStationController(StationboardService stationboardService) {
        this.stationboardService = stationboardService;
    }

    @GetMapping("/{id}/tablon")
    public ResponseEntity<List<StationboardDTO>> getStationboardByFavorite(
            @PathVariable Long id,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String type) {
        return ResponseEntity.ok(stationboardService.getStationboardByFavorite(id, limit, type));
    }
}
