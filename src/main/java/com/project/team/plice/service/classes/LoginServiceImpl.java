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

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByPhone(phone);
        if (optionalMember.isEmpty()) {
            throw new UsernameNotFoundException(phone);
        } else {
            Member member = optionalMember.get();
            return User.builder()
                    .username(member.getPhone())
                    .password(member.getPw())
                    .roles(member.getRole().toString())
                    .build();
        }
    }
}
