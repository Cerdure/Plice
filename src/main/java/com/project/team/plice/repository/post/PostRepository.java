package com.project.team.plice.repository.post;

import com.project.team.plice.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
