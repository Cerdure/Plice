package com.project.team.plice.repository.admin;

import com.project.team.plice.domain.admin.IP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPRepository extends JpaRepository<IP, Long> {
    public IP findByIp(String ip);
}
