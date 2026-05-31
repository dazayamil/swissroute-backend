package com.swissroute.backend.service;

import com.swissroute.backend.dto.request.FavoriteStationRequest;
import com.swissroute.backend.dto.response.FavoriteStationResponse;
import com.swissroute.backend.entity.FavoriteStation;
import com.swissroute.backend.entity.User;
import com.swissroute.backend.repository.FavoriteStationRepository;
import com.swissroute.backend.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteStationService {

    private final FavoriteStationRepository repository;
    private final UsuarioRepository usuarioRepository;

    public FavoriteStationService(FavoriteStationRepository repository,
                                  UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    public FavoriteStationResponse add(String email, FavoriteStationRequest request) {
        User user = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (repository.existsByUserIdAndExternalStationId(user.getId(), request.externalStationId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La estación ya está en favoritos");
        }

        FavoriteStation saved = repository.save(FavoriteStation.builder()
                .user(user)
                .externalStationId(request.externalStationId())
                .stationName(request.stationName())
                .build());

        return toResponse(saved);
    }

    public List<FavoriteStationResponse> list(String email) {
        User user = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        return repository.findByUserId(user.getId()).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private FavoriteStationResponse toResponse(FavoriteStation f) {
        return new FavoriteStationResponse(
                f.getId(),
                f.getExternalStationId(),
                f.getStationName(),
                f.getCreatedAt()
        );
    }
}
