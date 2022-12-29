package com.project.team.plice.repository.vote;

import com.project.team.plice.domain.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
