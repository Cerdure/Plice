package com.project.team.plice.repository.admin;

import com.project.team.plice.domain.admin.SearchKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, Long> {
    public SearchKeyword findByKeywordContainsIgnoreCase(String keyword);
    public List<SearchKeyword> findTop10ByOrderByCountDesc();
}
