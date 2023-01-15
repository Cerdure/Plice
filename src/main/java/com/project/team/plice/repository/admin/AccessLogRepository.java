package com.project.team.plice.repository.admin;

import com.project.team.plice.domain.admin.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
}
