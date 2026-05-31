package com.swissroute.backend.service;

import com.swissroute.backend.entity.SearchHistory;
import com.swissroute.backend.entity.User;
import com.swissroute.backend.repository.SearchHistoryRepository;
import com.swissroute.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class SearchHistoryService {

    private SearchHistoryRepository searchHistoryRepository;
    private UsuarioRepository usuarioRepository;

    public SearchHistoryService(SearchHistoryRepository searchHistoryRepository,
                                UsuarioRepository usuarioRepository){

        this.searchHistoryRepository = searchHistoryRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void saveHistory(
            String email,
            String origin,
            String destination,
            int numResults){

        User user = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        SearchHistory history = SearchHistory.builder()
                .user(user)
                .origin(origin)
                .destination(destination)
                .numResults(numResults)
                .build();

        searchHistoryRepository.save(history);
    }

    public Page<SearchHistory> getHistory(String email, Pageable pageable){

        User user = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        return searchHistoryRepository.findByUserIdOrderByQueryDateDesc(user.getId(), pageable);
    }

    public void deleteHistory(Long historyId, String email){

        User user = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        SearchHistory history = searchHistoryRepository.findById(historyId)
                .orElseThrow(() ->
                        new RuntimeException("Historial no encontrado"));

        if(!history.getUser().getId().equals(user.getId())){
            throw new RuntimeException("No autorizado");
        }

        searchHistoryRepository.delete(history);
    }

    public void deleteAllHistory(String email){

        User user = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        List<SearchHistory> history = searchHistoryRepository.findAllByUserId(user.getId());

        searchHistoryRepository.deleteAll(history);
    }
}
