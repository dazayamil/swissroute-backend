package com.swissroute.backend.service;

import com.swissroute.backend.entity.SearchHistory;
import com.swissroute.backend.entity.User;
import com.swissroute.backend.repository.SearchHistoryRepository;
import com.swissroute.backend.repository.UsuarioRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchHistoryServiceTest {

    @Mock
    private SearchHistoryRepository searchHistoryRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private SearchHistoryService searchHistoryService;

    @Test
    void saveHistory_success() {

        User user = new User();
        user.setId(1L);

        when(usuarioRepository.findByEmail("ana@gmail.com"))
                .thenReturn(Optional.of(user));

        searchHistoryService.saveHistory(
                "ana@gmail.com",
                "Lausanne",
                "Geneva",
                4
        );

        verify(searchHistoryRepository).save(any(SearchHistory.class));
    }

    @Test
    void getHistory_success() {

        User user = new User();
        user.setId(1L);

        Page<SearchHistory> page =
                new PageImpl<>(List.of(new SearchHistory()));

        when(usuarioRepository.findByEmail("ana@gmail.com"))
                .thenReturn(Optional.of(user));

        when(searchHistoryRepository
                .findByUserIdOrderByQueryDateDesc(
                        eq(1L),
                        any(Pageable.class)))
                .thenReturn(page);

        Page<SearchHistory> result =
                searchHistoryService.getHistory(
                        "ana@gmail.com",
                        PageRequest.of(0, 5));

        assertEquals(1, result.getContent().size());
    }

    @Test
    void deleteAllHistory_success() {

        User user = new User();
        user.setId(1L);

        List<SearchHistory> history =
                List.of(new SearchHistory());

        when(usuarioRepository.findByEmail("ana@gmail.com"))
                .thenReturn(Optional.of(user));

        when(searchHistoryRepository.findAllByUserId(1L))
                .thenReturn(history);

        searchHistoryService.deleteAllHistory(
                "ana@gmail.com"
        );

        verify(searchHistoryRepository)
                .deleteAll(history);
    }
}
