package com.project.team.plice.service.interfaces;

import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.member.MemberDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface MemberService {
    public Long join(MemberDto memberDto);
    void validateDuplicateMember(MemberDto memberDto);
    public Member findMember(Authentication authentication);
    public List<Member> findMembers();
    public Member findById(Long memberId);
    public Member findByPhone(String phone);
    public void update(Long id, MemberDto memberDto);
    public void delete(Long id);
}
