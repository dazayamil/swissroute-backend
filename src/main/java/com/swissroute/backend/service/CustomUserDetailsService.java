package com.swissroute.backend.service;

import com.swissroute.backend.entity.Usuario;
import com.swissroute.backend.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository repo;

    public CustomUserDetailsService(UsuarioRepository repo) {
        this.repo = repo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = repo.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException(
                        "Usuario no encontrado: "+username
                ));
        return User.builder()
                .username(u.getEmail())
                .password(u.getPasswordHash())
                .roles(u.getRole().toString())
                .build();
    }
}
