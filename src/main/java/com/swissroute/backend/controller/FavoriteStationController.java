package com.swissroute.backend.controller;

import com.swissroute.backend.dto.response.StationboardDTO;
import com.swissroute.backend.service.StationboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import com.swissroute.backend.dto.request.FavoriteStationRequest;
import com.swissroute.backend.dto.response.FavoriteStationResponse;
import com.swissroute.backend.service.FavoriteStationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estaciones-favoritas")
@Tag(name = "Favorite stations", description = "Authenticated user's favorite stations")
public class FavoriteStationController {
    private final StationboardService stationboardService;
    private final FavoriteStationService service;

    public FavoriteStationController(StationboardService stationboardService, FavoriteStationService service) {
        this.stationboardService = stationboardService;
        this.service = service;
    }

    @Operation(summary = "Get stationboard for a favorite station")
    @GetMapping("/{id}/tablon")
    public ResponseEntity<List<StationboardDTO>> getStationboardByFavorite(
            @PathVariable Long id,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String type) {
        return ResponseEntity.ok(stationboardService.getStationboardByFavorite(id, limit, type));
    }

    @Operation(summary = "Create a favorite station")
    @PostMapping
    public ResponseEntity<FavoriteStationResponse> add(
            @RequestBody FavoriteStationRequest request,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.add(authentication.getName(), request));
    }

    @Operation(summary = "List favorite stations")
    @GetMapping
    public ResponseEntity<List<FavoriteStationResponse>> list(Authentication authentication) {
        return ResponseEntity.ok(service.list(authentication.getName()));
    }

    @Operation(summary = "Delete a favorite station")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
