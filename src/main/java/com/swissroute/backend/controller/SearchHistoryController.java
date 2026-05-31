package com.swissroute.backend.controller;

import com.swissroute.backend.entity.SearchHistory;
import com.swissroute.backend.service.SearchHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
public class SearchHistoryController {

    private final SearchHistoryService searchHistoryService;

    public SearchHistoryController(SearchHistoryService searchHistoryService){

        this.searchHistoryService = searchHistoryService;
    }

    @GetMapping
    public ResponseEntity<Page<SearchHistory>> getHistory(Authentication authentication, Pageable pageable){

        return ResponseEntity.ok(
                searchHistoryService.getHistory(
                        authentication.getName(),
                        pageable
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistory(
            @PathVariable Long id,
            Authentication authentication){

        searchHistoryService.deleteHistory(
                id,
                authentication.getName()
        );

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllHistory(Authentication authentication){

        searchHistoryService.deleteAllHistory(
                authentication.getName()
        );

        return ResponseEntity.noContent().build();
    }
}
