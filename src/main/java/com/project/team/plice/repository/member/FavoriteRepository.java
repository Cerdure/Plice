package com.project.team.plice.repository.member;

import com.project.team.plice.domain.member.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
