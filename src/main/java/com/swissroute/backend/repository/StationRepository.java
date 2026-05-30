package com.swissroute.backend.repository;

import com.swissroute.backend.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StationRepository extends JpaRepository<Station, Long>{
}
