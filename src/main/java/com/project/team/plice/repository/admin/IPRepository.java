package com.project.team.plice.repository.admin;

import com.project.team.plice.domain.admin.Blacklist;
import com.project.team.plice.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {
    public Blacklist findByMember(Member member);
    public Blacklist findByIp(String ip);
    public Page<Blacklist> findAllByMemberNotNullOrderByRegDateDesc(Pageable pageable);
    public Page<Blacklist> findAllByIpNotNullOrderByRegDateDesc(Pageable pageable);
}
