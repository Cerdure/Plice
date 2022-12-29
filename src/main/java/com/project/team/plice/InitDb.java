package com.project.team.plice;

import com.project.team.plice.domain.enums.MemberRole;
import com.project.team.plice.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInitBooks();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final BCryptPasswordEncoder passwordEncoder;

        public void dbInitBooks() {

            Member member = Member.builder()
                    .name("테스터")
                    .nickname("유저11")
                    .phone("01012345678")
                    .birth("901020")
                    .sex("1")
                    .email("user11@mail.com")
                    .pw(passwordEncoder.encode("1234"))
                    .role(MemberRole.USER)
                    .build();

            em.persist(member);
        }
    }
}