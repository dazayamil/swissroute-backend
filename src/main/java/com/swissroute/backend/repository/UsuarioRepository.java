package com.swissroute.backend.repository;

import com.swissroute.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
