package com.project.team.plice.repository.search;

import com.project.team.plice.domain.news.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRepository extends JpaRepository<NewsEntity, Long> {
}
