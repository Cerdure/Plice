package com.project.team.plice.repository.admin;

import com.project.team.plice.domain.admin.Authority;
import com.project.team.plice.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    public Authority findByMember(Member member);
}
