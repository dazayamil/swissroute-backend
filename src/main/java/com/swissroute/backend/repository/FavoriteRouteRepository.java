package com.swissroute.backend.repository;

import com.swissroute.backend.entity.FavoriteRoute;
import com.swissroute.backend.entity.FavoriteStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRouteRepository extends JpaRepository<FavoriteRoute, Long> {
    List<FavoriteRoute> findByUserId(Long userId);
}
