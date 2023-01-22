package com.project.team.plice.service.classes;

import com.project.team.plice.domain.enums.MemberRole;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.member.MemberDto;
import com.project.team.plice.repository.member.MemberRepository;
import com.project.team.plice.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public boolean join(MemberDto memberDto) {
        boolean result = validateDuplicateMember(memberDto);
        Member member = memberDto.createMember(passwordEncoder);
        memberRepository.save(member);
        return result;
    }

    public boolean validateDuplicateMember(MemberDto memberDto) {
        boolean isDuplicated = false;
        Optional<Member> findMembers = memberRepository.findByPhone(memberDto.getPhone());
        if (!findMembers.isEmpty()) {
            isDuplicated = true;
        }
        return isDuplicated;
    }

    @Override
    public Member findMember(Authentication authentication) {
        return findByPhone(authentication.getName());
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Override
    public List<Member> findByRoles(List<MemberRole> roles) {
        return memberRepository.findByRoleIn(roles);
    }

    @Override
    public Page<Member> findByRoles(List<MemberRole> roles, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable= PageRequest.of(page,12, Sort.by("regDate").descending());
        return memberRepository.findByRoleIn(roles, pageable);
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    @Override
    public Member findByPhone(String phone) {
        return memberRepository.findByPhone(phone).get();
    }

    @Override
    @Transactional
    public void update(Authentication authentication, MemberDto memberDto) {
        String phone = authentication.getName();
        Member member = memberRepository.findByPhone(phone).get();
        member.update(memberDto.getName(),
                memberDto.getNickname());
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public void update(MemberDto memberDto) {
        Member member = memberRepository.findById(memberDto.getId()).get();
        member.updatePhone(memberDto.getPhone());
        member.updateName(memberDto.getName());
        member.updateNickname(memberDto.getNickname());
        member.updateBirth(memberDto.getBirth());
        memberRepository.save(member);
    }

    @Transactional
    public void delete(Authentication authentication){
        String phone = authentication.getName();
        Member member = memberRepository.findByPhone(phone).get();
        memberRepository.delete(member);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        memberRepository.delete(memberRepository.findById(id).get());
    }


}