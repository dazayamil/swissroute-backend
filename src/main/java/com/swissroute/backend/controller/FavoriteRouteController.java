package com.swissroute.backend.controller;

import com.swissroute.backend.dto.request.CreateFavoriteRouteRequest;
import com.swissroute.backend.dto.response.FavoriteRouteResponse;
import com.swissroute.backend.service.FavoriteRouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<FavoriteRouteResponse>> getFavoriteRoutes(Authentication authentication){

        return ResponseEntity.ok(
                favoriteRouteService.getFavoriteRoutes(authentication.getName())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFavoriteRoute(
            @PathVariable Long id,
            @RequestBody CreateFavoriteRouteRequest request,
            Authentication authentication
    ){
        favoriteRouteService.updateFavoriteRoute(
                id,
                authentication.getName(),
                request
        );
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavoriteRoute(
            @PathVariable Long id,
            Authentication authentication){

        favoriteRouteService.deleteFavoriteRoute(id, authentication.getName());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
