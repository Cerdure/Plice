package com.project.team.plice.domain.member;

import com.project.team.plice.domain.chat.ChatRoom;
import com.project.team.plice.domain.enums.MemberRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Member implements UserDetails {

    @Id @GeneratedValue
    @Column(name = "member_id")
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

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "favorite_id")
    private Favorite favorite;

    private String profileImgPath;

    @PrePersist                  // null일 때 default 값
    public void prePersist() {
        this.role = this.role == null ? MemberRole.USER : this.role;
        this.profileImgPath = this.profileImgPath == null ? "/img/icon/profile.png" : this.profileImgPath;
        this.regDate = this.regDate == null ? LocalDate.now() : this.regDate;
    }

    @Builder
    public Member(Long id, String phone, String pw, String name, String nickname, String birth, String sex, String email, LocalDate regDate, LocalDate delDate, MemberRole role, Favorite favorite, String profileImgPath) {
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
        this.profileImgPath = profileImgPath;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }
    @Override
    public String getUsername() {
        return null;
    }
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
    @Override
    public boolean isEnabled() {
        return false;
    }

    public void changeNickname(String nickname){
        this.nickname = nickname;
    }

    public void changeName(String name) {
        this.name = name;
    }
}
