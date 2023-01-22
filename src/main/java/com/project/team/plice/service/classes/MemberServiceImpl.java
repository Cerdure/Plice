package com.project.team.plice.service.classes;

import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.member.MemberDto;
import com.project.team.plice.repository.member.MemberRepository;
import com.project.team.plice.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public Long join(MemberDto memberDto) {
        validateDuplicateMember(memberDto);
        Member member = memberDto.createMember(passwordEncoder);
        memberRepository.save(member);
        return member.getId();
    }

    public void validateDuplicateMember(MemberDto memberDto) {
        Optional<Member> findMembers = memberRepository.findByPhone(memberDto.getPhone());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    @Override
    public Member findMember(Authentication authentication) {
        return findByPhone(authentication.getName());
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    @Override
    public Member findByPhone(String phone) {
        return memberRepository.findByPhone(phone).get();
    }

    @Transactional
    public void update(Authentication authentication, MemberDto memberDto) {
        String phone = authentication.getName();
        Member member = memberRepository.findByPhone(phone).get();
            member.update(memberDto.getName(),
                        memberDto.getNickname());
            if(memberDto.getPw() != null && memberDto.getPw() != ""){
            member.updatePw(passwordEncoder.encode(memberDto.getPw()));
            }
        memberRepository.save(member);
        System.out.println("member = " + memberDto.getPw());

        }



    @Transactional
    public void delete(Authentication authentication){
        String phone = authentication.getName();
        Member member = memberRepository.findByPhone(phone).get();
        memberRepository.delete(member);
    }


}