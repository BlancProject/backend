package com.blanc.market.domain.searchHistory.repository;

import com.blanc.market.domain.searchHistory.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

}
