package com.project.team.plice.dto.member;

import com.project.team.plice.domain.enums.MemberRole;
import com.project.team.plice.domain.member.Favorite;
import com.project.team.plice.domain.member.Member;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
public class MemberDto {

    private Long id;
    private String phone;  //실제 로그인 아이디
    private String pw;
    private String name;
    private String nickname;
    private String birth;
    private String sex;
    private String email;
    private LocalDate regDate;
    private LocalDate delDate;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private Favorite favorite;

    @Builder
    public MemberDto(Long id, String phone, String pw, String name, String nickname, String birth, String sex, String email, LocalDate regDate, LocalDate delDate, MemberRole role, Favorite favorite) {
        this.id = id;
        this.phone = phone;
        this.pw = pw;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.sex = sex;
        this.email = email;
        this.regDate = regDate;
        this.delDate = delDate;
        this.role = role;
        this.favorite = favorite;
    }

    public Member createMember(PasswordEncoder passwordEncoder){
        return Member.builder()
                .pw(passwordEncoder.encode(this.pw))
                .phone(this.phone)
                .pw(this.pw)
                .name(this.name)
                .nickname(this.nickname)
                .birth(this.birth)
                .sex(this.sex)
                .email(this.email)
                .regDate(this.regDate)
                .delDate(this.delDate)
                .role(this.role)
                .favorite(this.favorite)
                .build();
    }
}
