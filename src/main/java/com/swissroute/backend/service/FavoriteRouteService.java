package com.swissroute.backend.service;

import com.swissroute.backend.dto.request.CreateFavoriteRouteRequest;
import com.swissroute.backend.dto.response.FavoriteRouteResponse;
import com.swissroute.backend.entity.FavoriteRoute;
import com.swissroute.backend.entity.User;
import com.swissroute.backend.exception.DuplicateFavoriteRouteException;
import com.swissroute.backend.exception.FavoriteRouteAccessDeniedException;
import com.swissroute.backend.exception.FavoriteRouteNotFoundException;
import com.swissroute.backend.exception.UserNotFoundException;
import com.swissroute.backend.repository.FavoriteRouteRepository;
import com.swissroute.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

        validateName(request.getName());

        User user = getUserByEmail(email);

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

    public List<FavoriteRouteResponse> getFavoriteRoutes(String email){

        User user = getUserByEmail(email);

        return favoriteRouteRepository.findByUserId(user.getId())
                .stream()
                .map(route -> FavoriteRouteResponse.builder()
                        .id(route.getId())
                        .name(route.getName())
                        .origin(route.getOrigin())
                        .destination(route.getDestination())
                        .transportType(route.getTransportType())
                        .build())
                .toList();
    }

    public void updateFavoriteRoute(Long routeId, String email, CreateFavoriteRouteRequest request){

        validateName(request.getName());

        User user = getUserByEmail(email);

        FavoriteRoute route = getFavoriteRoute(routeId);

        validateAccess(route, user);

        route.setName(request.getName());
        route.setOrigin(request.getOrigin());
        route.setDestination(request.getDestination());
        route.setTransportType(request.getTransportType());

        favoriteRouteRepository.save(route);
    }


    public void deleteFavoriteRoute(Long routeId, String email){
        User user = getUserByEmail(email);
        FavoriteRoute route = getFavoriteRoute(routeId);

        validateAccess(route, user);


        favoriteRouteRepository.delete(route);

    }

    private void validateName(String name){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
    }

    private User getUserByEmail(String email){
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
    }

    private FavoriteRoute getFavoriteRoute(Long routeId){
        return favoriteRouteRepository.findById(routeId)
                .orElseThrow(() -> new FavoriteRouteNotFoundException("Ruta favorita no encontrada"));
    }

    private void validateAccess(FavoriteRoute route, User user){
        if(!route.getUser().getId().equals(user.getId())){
            throw new FavoriteRouteAccessDeniedException("No tiene permisos para modificar o eliminar esta ruta");
        }
    }
}
