package com.project.team.plice.repository.post;

import com.project.team.plice.domain.post.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    public List<Notice> findByRegDateBeforeOrderByRegDateDesc(LocalDateTime regDate);
    public List<Notice> findByRegDateAfter(LocalDateTime regDate);
    public Page<Notice> findById(Long id, Pageable pageable);
    public Page<Notice> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    public Page<Notice> findByContentContainingIgnoreCase(String content, Pageable pageable);
    public Page<Notice> findByMemberNicknameContainsIgnoreCase(String nickname, Pageable pageable);
    public List<Notice> findTop5ByOrderByRegDateDesc();
}
