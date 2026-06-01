package com.swissroute.backend.controller;

import com.swissroute.backend.dto.request.CreateFavoriteRouteRequest;
import com.swissroute.backend.service.FavoriteRouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/rutas-favoritas")
public class FavoriteRouteController {

    private final FavoriteRouteService favoriteRouteService;

    public FavoriteRouteController(FavoriteRouteService favoriteRouteService){
        this.favoriteRouteService = favoriteRouteService;
    }

    @PostMapping
    public ResponseEntity<Void> createFavoriteRoute(
            @RequestBody CreateFavoriteRouteRequest request,
            Authentication authentication){

        favoriteRouteService.createFavoriteRoute(
                authentication.getName(),
                request
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
