package com.project.team.plice.service.interfaces;

import com.project.team.plice.domain.enums.MemberRole;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.member.MemberDto;
import com.project.team.plice.utils.DataUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface MemberService {
    public boolean join(MemberDto memberDto);
    boolean validateDuplicateMember(MemberDto memberDto);

    public Member findMember(Authentication authentication);
    public List<Member> findMembers();
    public List<Member> findByRoles(List<MemberRole> roles);
    public Page<Member> findByRoles(List<MemberRole> roles, Pageable pageable);
    public Member findById(Long memberId);
    public Member findByPhone(String phone);
    public Page<Member> searchMember(DataUtil dataUtil, List<MemberRole> roles, Pageable pageable);

    public void update(Authentication authentication, MemberDto memberDto);
    public void update(MemberDto memberDto);
    public void update(String phone, String pw);
    public void delete(Authentication authentication);
    public void delete(Long id);

    public Boolean advanceValidate(String phone, String pw);
    public String checkPhone(String idInput);
    public String checkNick(String nickInput);
    public String certifiedPhoneNumber(String phoneNumber);
}
