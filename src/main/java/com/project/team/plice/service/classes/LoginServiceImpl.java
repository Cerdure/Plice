package com.project.team.plice.service.classes;

import com.project.team.plice.domain.member.Member;
import com.project.team.plice.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Member member = memberRepository.findByPhone(phone).get();
        System.out.println("member.toString() = " + member.toString());
        if (member == null) {
            throw new UsernameNotFoundException(phone);
        }
        return User.builder()
                .username(member.getPhone())
                .password(member.getPw())
                .roles(member.getRole().toString())
                .build();
    }
    }
