package com.project.team.plice.repository.admin;

import com.project.team.plice.domain.admin.Blacklist;
import com.project.team.plice.domain.admin.IP;
import com.project.team.plice.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {
    public Page<Blacklist> findAllByMemberNotNullOrderByRegDateDesc(Pageable pageable);
    public Page<Blacklist> findByIdAndMemberNotNull(Long id, Pageable pageable);
    public Page<Blacklist> findByMemberId(Long memberId, Pageable pageable);
    public Blacklist findByMember(Member member);
    public Page<Blacklist> findAllByIpNotNullOrderByRegDateDesc(Pageable pageable);
    public Page<Blacklist> findByIdAndIpNotNull(Long id, Pageable pageable);
    public Page<Blacklist> findByIp(IP ip, Pageable pageable);
    public Blacklist findByIp(IP ip);
}
