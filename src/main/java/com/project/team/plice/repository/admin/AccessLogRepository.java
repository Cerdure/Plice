package com.project.team.plice.repository.admin;

import com.project.team.plice.domain.admin.AccessLog;
import com.project.team.plice.domain.admin.IP;
import com.project.team.plice.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
    public AccessLog findTopByMemberOrderByRegDateDesc(Member member);
    public Page<AccessLog> findAllByOrderByRegDateDesc(Pageable pageable);
    public List<AccessLog> findByRegDateAfter(LocalDateTime localDateTime);
    public Page<AccessLog> findById(Long id, Pageable pageable);
    public Page<AccessLog> findByMember(Member member, Pageable pageable);
    public Page<AccessLog> findByIp(IP ip, Pageable pageable);
}
