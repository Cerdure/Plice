package com.project.team.plice.repository.post;

import com.project.team.plice.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    public Page<Post> findById(Long id, Pageable pageable);
    public List<Post> findByRegDateAfter(LocalDateTime regDate);
    public List<Post> findByRegDateBeforeOrderByRegDateDesc(LocalDateTime regDate);
    public Page<Post> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    public Page<Post> findByContentContainingIgnoreCase(String content, Pageable pageable);
    public Page<Post> findByMemberNicknameContainsIgnoreCase(String nickname, Pageable pageable);
}
