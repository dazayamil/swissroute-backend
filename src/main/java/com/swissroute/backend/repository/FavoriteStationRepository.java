package com.swissroute.backend.repository;

import com.swissroute.backend.entity.FavoriteStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteStationRepository extends JpaRepository<FavoriteStation, Long> {
    List<FavoriteStation> findByUserId(Long userId);
}
