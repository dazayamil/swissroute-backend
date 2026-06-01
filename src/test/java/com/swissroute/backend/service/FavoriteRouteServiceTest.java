package com.swissroute.backend.service;

import com.swissroute.backend.dto.request.CreateFavoriteRouteRequest;
import com.swissroute.backend.dto.response.FavoriteRouteResponse;
import com.swissroute.backend.entity.FavoriteRoute;
import com.swissroute.backend.entity.User;
import com.swissroute.backend.enums.TransportType;
import com.swissroute.backend.exception.DuplicateFavoriteRouteException;
import com.swissroute.backend.exception.FavoriteRouteAccessDeniedException;
import com.swissroute.backend.repository.FavoriteRouteRepository;
import com.swissroute.backend.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
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

    @Test
    void getFavoriteRoutes_success() {

        User user = new User();
        user.setId(1L);

        FavoriteRoute route = FavoriteRoute.builder()
                .id(1L)
                .name("Trabajo")
                .origin("Lausanne")
                .destination("Geneva")
                .transportType(TransportType.TRAIN)
                .user(user)
                .build();

        when(usuarioRepository.findByEmail("john@gmail.com"))
                .thenReturn(Optional.of(user));

        when(favoriteRouteRepository.findByUserId(1L))
                .thenReturn(List.of(route));

        List<FavoriteRouteResponse> result =
                favoriteRouteService.getFavoriteRoutes(
                        "john@gmail.com");

        assertEquals(1, result.size());
        assertEquals("Trabajo", result.get(0).getName());
    }

    @Test
    void updateFavoriteRoute_success() {

        User user = new User();
        user.setId(1L);

        FavoriteRoute route = new FavoriteRoute();
        route.setId(1L);
        route.setUser(user);

        CreateFavoriteRouteRequest request =
                new CreateFavoriteRouteRequest(
                        "Trabajo Nuevo",
                        "Lausanne",
                        "Zurich",
                        TransportType.TRAIN
                );

        when(usuarioRepository.findByEmail("john@gmail.com"))
                .thenReturn(Optional.of(user));

        when(favoriteRouteRepository.findById(1L))
                .thenReturn(Optional.of(route));

        favoriteRouteService.updateFavoriteRoute(
                1L,
                "john@gmail.com",
                request
        );

        verify(favoriteRouteRepository)
                .save(route);
    }

    @Test
    void updateFavoriteRoute_accessDenied() {

        User owner = new User();
        owner.setId(1L);

        User loggedUser = new User();
        loggedUser.setId(2L);

        FavoriteRoute route = new FavoriteRoute();
        route.setUser(owner);

        CreateFavoriteRouteRequest request =
                new CreateFavoriteRouteRequest(
                        "Trabajo",
                        "Lausanne",
                        "Zurich",
                        TransportType.TRAIN
                );

        when(usuarioRepository.findByEmail("otro@gmail.com"))
                .thenReturn(Optional.of(loggedUser));

        when(favoriteRouteRepository.findById(1L))
                .thenReturn(Optional.of(route));

        assertThrows(
                FavoriteRouteAccessDeniedException.class,
                () -> favoriteRouteService.updateFavoriteRoute(
                        1L,
                        "otro@gmail.com",
                        request
                )
        );
    }

    @Test
    void deleteFavoriteRoute_success() {

        User user = new User();
        user.setId(1L);

        FavoriteRoute route = new FavoriteRoute();
        route.setUser(user);

        when(usuarioRepository.findByEmail("john@gmail.com"))
                .thenReturn(Optional.of(user));

        when(favoriteRouteRepository.findById(1L))
                .thenReturn(Optional.of(route));

        favoriteRouteService.deleteFavoriteRoute(
                1L,
                "john@gmail.com"
        );

        verify(favoriteRouteRepository)
                .delete(route);
    }

    @Test
    void deleteFavoriteRoute_accessDenied() {

        User owner = new User();
        owner.setId(1L);

        User loggedUser = new User();
        loggedUser.setId(2L);

        FavoriteRoute route = new FavoriteRoute();
        route.setUser(owner);

        when(usuarioRepository.findByEmail("otro@gmail.com"))
                .thenReturn(Optional.of(loggedUser));

        when(favoriteRouteRepository.findById(1L))
                .thenReturn(Optional.of(route));

        assertThrows(
                FavoriteRouteAccessDeniedException.class,
                () -> favoriteRouteService.deleteFavoriteRoute(
                        1L,
                        "otro@gmail.com"
                )
        );
    }
}
