package com.swissroute.backend.controller;

import com.swissroute.backend.dto.response.StationDTO;
import com.swissroute.backend.dto.response.StationResponse;
import com.swissroute.backend.service.StationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stations")
@CrossOrigin("*")
@Tag(name = "Stations", description = "Station search endpoints")
public class StationController {
    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @Operation(summary = "Search stations by name")
    @GetMapping
    public ResponseEntity<List<StationDTO>> searchByName(@RequestParam String query) {
        return ResponseEntity.ok(stationService.searchByName(query));
    }

    @Operation(summary = "Search stations by coordinates or query")
    @GetMapping("/search")
    public ResponseEntity<List<StationResponse>> search(
            @RequestParam(required = false) Double x,
            @RequestParam(required = false) Double y,
            @RequestParam(required = false) String query) {

        return ResponseEntity.ok(stationService.searchByCoordinates(x, y, query));
    }
}
