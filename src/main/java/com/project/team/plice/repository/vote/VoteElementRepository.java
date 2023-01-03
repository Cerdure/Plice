package com.project.team.plice.repository.vote;

import com.project.team.plice.domain.vote.VoteElement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteElementRepository extends JpaRepository<VoteElement, Long> {
}
