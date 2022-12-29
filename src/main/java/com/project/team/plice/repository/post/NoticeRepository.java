package com.project.team.plice.repository.post;

import com.project.team.plice.domain.post.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
