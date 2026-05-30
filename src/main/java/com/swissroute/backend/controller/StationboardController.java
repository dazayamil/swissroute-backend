package com.swissroute.backend.controller;

import com.swissroute.backend.dto.response.StationboardDTO;
import com.swissroute.backend.service.StationboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tablon")
public class StationboardController {

    private final StationboardService stationboardService;

    public StationboardController(StationboardService stationboardService) {
        this.stationboardService = stationboardService;
    }

    @GetMapping
    public ResponseEntity<List<StationboardDTO>> getStationboard(
            @RequestParam String station,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String type) {

        return ResponseEntity.ok(stationboardService.getStationboard(station, limit, type));
    }
}
