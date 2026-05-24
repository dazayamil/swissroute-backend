package com.swissroute.backend.controller;

import com.swissroute.backend.dto.response.StationResponse;
import com.swissroute.backend.service.StationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stations")
@CrossOrigin("*")
public class StationController {
    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<StationResponse>> search(
            @RequestParam(required = false) Double x,
            @RequestParam(required = false) Double y,
            @RequestParam(required = false) String query) {

        return ResponseEntity.ok(stationService.searchByCoordinates(x, y, query));
    }
}
