package com.project.team.plice.dto.member;

import com.project.team.plice.domain.admin.Authority;
import com.project.team.plice.domain.enums.MemberRole;
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
    private LocalDate regDate;
    private LocalDate delDate;
    private String profileImgPath;

    private Long teamNumber;
    private Boolean adminMng;
    private Boolean memberMng;
    private Boolean chatMng;
    private Boolean postMng;
    private Boolean inquiryMng;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Builder
    public MemberDto(Long id, String phone, String pw, String name, String nickname, String birth, LocalDate regDate, LocalDate delDate, String profileImgPath, MemberRole role) {
        this.id = id;
        this.phone = phone;
        this.pw = pw;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.regDate = regDate;
        this.delDate = delDate;
        this.profileImgPath = profileImgPath;
        this.role = role;
    }

    public Member toEntity() {
        Member build = Member.builder()
                .id(id)
                .phone(phone)
                .pw(pw)
                .name(name)
                .nickname(nickname)
                .birth(birth)
                .regDate(regDate)
                .delDate(delDate)
                .profileImgPath(profileImgPath)
                .role(role)
                .build();
        return build;
    }

    public Member createMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .pw(passwordEncoder.encode(this.pw))
                .phone(this.phone)
                .name(this.name)
                .nickname(this.nickname)
                .birth(this.birth)
                .regDate(this.regDate)
                .delDate(this.delDate)
                .role(this.role)
                .profileImgPath(this.profileImgPath)
                .build();
    }

    public Authority getAuthorities() {
        return Authority.builder()
                .adminMng(this.adminMng)
                .memberMng(this.memberMng)
                .chatMng(this.chatMng)
                .postMng(this.postMng)
                .inquiryMng(this.inquiryMng)
                .build();
    }
}
