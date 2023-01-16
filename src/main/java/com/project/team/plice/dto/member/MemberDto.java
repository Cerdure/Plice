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
import java.util.List;

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
    private List<Favorite> favorite;
    private String profileImgPath;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Builder
    public MemberDto(Long id, String phone, String pw, String name, String nickname, String birth, String sex, String email, LocalDate regDate, LocalDate delDate, List<Favorite> favorite, String profileImgPath, MemberRole role) {
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
        this.favorite = favorite;
        this.profileImgPath = profileImgPath;
        this.role = role;
    }

    public Member createMember(PasswordEncoder passwordEncoder){
        return Member.builder()
                .pw(passwordEncoder.encode(this.pw))
                .phone(this.phone)
                .name(this.name)
                .nickname(this.nickname)
                .birth(this.birth)
                .sex(this.sex)
                .email(this.email)
                .regDate(this.regDate)
                .delDate(this.delDate)
                .role(this.role)
                .favorite(this.favorite)
                .profileImgPath(this.profileImgPath)
                .build();
    }
}
