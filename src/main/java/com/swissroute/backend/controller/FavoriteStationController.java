package com.swissroute.backend.controller;

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
public class FavoriteStationController {

    private final FavoriteStationService service;

    public FavoriteStationController(FavoriteStationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<FavoriteStationResponse> add(
            @RequestBody FavoriteStationRequest request,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.add(authentication.getName(), request));
    }

    @GetMapping
    public ResponseEntity<List<FavoriteStationResponse>> list(Authentication authentication) {
        return ResponseEntity.ok(service.list(authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
