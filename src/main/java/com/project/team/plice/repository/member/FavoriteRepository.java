package com.project.team.plice.repository.member;

import com.project.team.plice.domain.inquire.Inquire;
import com.project.team.plice.domain.member.Favorite;
import com.project.team.plice.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
