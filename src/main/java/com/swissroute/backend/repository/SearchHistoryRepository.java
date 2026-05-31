package com.swissroute.backend.repository;

import com.swissroute.backend.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    Page<SearchHistory> findByUserIdOrderByQueryDateDesc(Long userId, Pageable pageable);

    List<SearchHistory> findAllByUserId(Long userId);
}
