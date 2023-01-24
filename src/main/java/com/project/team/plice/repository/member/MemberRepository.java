package com.project.team.plice.repository.member;


import com.project.team.plice.domain.enums.MemberRole;
import com.project.team.plice.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Optional<Member> findByPhone(String phone);
    public List<Member> findByNickname(String nickname);
    public List<Member> findByRoleIn(List<MemberRole> roles);
    public Page<Member> findByRoleIn(List<MemberRole> roles, Pageable pageable);
    public Page<Member> findByIdAndRoleIn(Long id, List<MemberRole> roles, Pageable pageable);
    public Page<Member> findByPhoneAndRoleIn(String phone, List<MemberRole> roles, Pageable pageable);
    public Page<Member> findByNameContainsIgnoreCaseAndRoleIn(String name, List<MemberRole> roles, Pageable pageable);
    public Page<Member> findByNicknameContainsIgnoreCaseAndRoleIn(String nickname, List<MemberRole> roles, Pageable pageable);
    public Long countByRegDate(LocalDate regDate);
}
