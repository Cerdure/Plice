package com.project.team.plice.service.interfaces;

import com.project.team.plice.domain.enums.MemberRole;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.member.MemberDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface MemberService {
    public boolean join(MemberDto memberDto);
    boolean validateDuplicateMember(MemberDto memberDto);
    public Member findMember(Authentication authentication);
    public List<Member> findMembers();
    public List<Member> findByRole(MemberRole role);
    public Member findById(Long memberId);
    public Member findByPhone(String phone);
    public void update(MemberDto memberDto);
    public void delete(Long id);
}
