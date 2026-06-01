package com.swissroute.backend.service;

import com.swissroute.backend.dto.request.CreateFavoriteRouteRequest;
import com.swissroute.backend.entity.FavoriteRoute;
import com.swissroute.backend.entity.User;
import com.swissroute.backend.enums.TransportType;
import com.swissroute.backend.exception.DuplicateFavoriteRouteException;
import com.swissroute.backend.repository.FavoriteRouteRepository;
import com.swissroute.backend.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FavoriteRouteServiceTest {

    @Mock
    private FavoriteRouteRepository favoriteRouteRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private FavoriteRouteService favoriteRouteService;

    @Test
    void createFavoriteRoute_success() {

        User user = new User();
        user.setId(1L);

        CreateFavoriteRouteRequest request =
                new CreateFavoriteRouteRequest(
                        "Trabajo",
                        "Lausanne",
                        "Geneva",
                        TransportType.TRAIN
                );

        when(usuarioRepository.findByEmail("john@gmail.com"))
                .thenReturn(Optional.of(user));

        when(favoriteRouteRepository.existsByUserIdAndName(
                1L,
                "Trabajo"))
                .thenReturn(false);

        favoriteRouteService.createFavoriteRoute(
                "john@gmail.com",
                request
        );

        verify(favoriteRouteRepository)
                .save(any(FavoriteRoute.class));
    }

    @Test
    void createFavoriteRoute_duplicateName() {

        User user = new User();
        user.setId(1L);

        CreateFavoriteRouteRequest request =
                new CreateFavoriteRouteRequest(
                        "Trabajo",
                        "Lausanne",
                        "Geneva",
                        TransportType.TRAIN
                );

        when(usuarioRepository.findByEmail("john@gmail.com"))
                .thenReturn(Optional.of(user));

        when(favoriteRouteRepository.existsByUserIdAndName(
                1L,
                "Trabajo"))
                .thenReturn(true);

        assertThrows(
                DuplicateFavoriteRouteException.class,
                () -> favoriteRouteService.createFavoriteRoute(
                        "john@gmail.com",
                        request
                )
        );
    }

    @Test
    void createFavoriteRoute_emptyName() {

        CreateFavoriteRouteRequest request =
                new CreateFavoriteRouteRequest(
                        "",
                        "Lausanne",
                        "Geneva",
                        TransportType.TRAIN
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> favoriteRouteService.createFavoriteRoute(
                        "john@gmail.com",
                        request
                )
        );
    }
}
