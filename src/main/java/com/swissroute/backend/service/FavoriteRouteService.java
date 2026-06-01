package com.swissroute.backend.service;

import com.swissroute.backend.dto.request.CreateFavoriteRouteRequest;
import com.swissroute.backend.entity.FavoriteRoute;
import com.swissroute.backend.entity.User;
import com.swissroute.backend.exception.DuplicateFavoriteRouteException;
import com.swissroute.backend.repository.FavoriteRouteRepository;
import com.swissroute.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class FavoriteRouteService {

    private final FavoriteRouteRepository favoriteRouteRepository;
    private final UsuarioRepository usuarioRepository;

    public FavoriteRouteService(
            FavoriteRouteRepository favoriteRouteRepository,
            UsuarioRepository usuarioRepository
    ){
        this.favoriteRouteRepository = favoriteRouteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void createFavoriteRoute(
            String email,
            CreateFavoriteRouteRequest request
    ){

        if(request.getName() == null || request.getName().isBlank()){
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        User user = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if(favoriteRouteRepository.existsByUserIdAndName(
                user.getId(),
                request.getName())){
            throw new DuplicateFavoriteRouteException(
                    "Ya existe una ruta con ese nombre"
            );
        }

        FavoriteRoute route = FavoriteRoute.builder()
                .user(user)
                .name(request.getName())
                .origin(request.getOrigin())
                .destination(request.getDestination())
                .transportType(request.getTransportType())
                .build();

        favoriteRouteRepository.save(route);

    }


}
