package com.project.team.plice.repository.admin;

import com.project.team.plice.domain.admin.AccessLog;
import com.project.team.plice.domain.admin.Blacklist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
    public Page<AccessLog> findAllByOrderByRegDateDesc(Pageable pageable);
    public List<AccessLog> findByRegDateAfter(LocalDateTime localDateTime);
    public Long countByRegDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
    public Long countByUriContainingIgnoreCaseAndRegDateBetween(String uri ,LocalDateTime startDateTime, LocalDateTime endDateTime);
}
