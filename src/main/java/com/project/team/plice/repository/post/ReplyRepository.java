package com.project.team.plice.repository.post;

import com.project.team.plice.domain.post.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
