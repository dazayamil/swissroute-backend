package com.swissroute.backend.controller;

import com.swissroute.backend.dto.request.CreateFavoriteRouteRequest;
import com.swissroute.backend.dto.response.FavoriteRouteResponse;
import com.swissroute.backend.service.FavoriteRouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rutas-favoritas")
@Tag(name = "Favorite routes", description = "Authenticated user's favorite routes")
public class FavoriteRouteController {

    private final FavoriteRouteService favoriteRouteService;

    public FavoriteRouteController(FavoriteRouteService favoriteRouteService){
        this.favoriteRouteService = favoriteRouteService;
    }

    @Operation(summary = "Create a favorite route")
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

    @Operation(summary = "List favorite routes")
    @GetMapping
    public ResponseEntity<List<FavoriteRouteResponse>> getFavoriteRoutes(Authentication authentication){

        return ResponseEntity.ok(
                favoriteRouteService.getFavoriteRoutes(authentication.getName())
        );
    }

    @Operation(summary = "Update a favorite route")
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

    @Operation(summary = "Delete a favorite route")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavoriteRoute(
            @PathVariable Long id,
            Authentication authentication){

        favoriteRouteService.deleteFavoriteRoute(id, authentication.getName());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
