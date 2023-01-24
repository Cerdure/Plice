package com.project.team.plice.repository.admin;

import com.project.team.plice.domain.admin.Report;
import com.project.team.plice.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    public Page<Report> findById(Long id, Pageable pageable);
    public Page<Report> findByReporterId(Long reporterId, Pageable pageable);
}
