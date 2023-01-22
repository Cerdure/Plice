package com.project.team.plice.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.team.plice.domain.admin.AccessLog;
import com.project.team.plice.domain.admin.AdminTeam;
import com.project.team.plice.domain.admin.Authority;
import com.project.team.plice.domain.admin.Blacklist;
import com.project.team.plice.domain.admin.Report;
import com.project.team.plice.domain.chat.MemberChatRoom;
import com.project.team.plice.domain.enums.MemberRole;
import com.project.team.plice.domain.inquire.Inquire;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements UserDetails {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String phone;  //실제 로그인 아이디

    private String pw;

    private String name;

    private String nickname;

    private String birth;

    private LocalDate regDate;

    private LocalDate delDate;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Favorite> favorite;

    @OneToMany(mappedBy = "reporter", orphanRemoval = true)
    @JsonIgnore
    private List<Report> reports;

    @OneToOne(mappedBy = "member", orphanRemoval = true)
    private Authority authority;

    private String profileImgPath;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    @JsonIgnore
    private List<MemberChatRoom> memberChatRoom;

    @OneToOne(mappedBy = "member", orphanRemoval = true)
    private Blacklist blacklist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_team_id")
    private AdminTeam adminTeam;

    @PrePersist
    public void prePersist() {
        this.role = this.role == null ? MemberRole.USER : this.role;
        this.profileImgPath = this.profileImgPath == null ? "/img/icon/profile.png" : this.profileImgPath;
        this.regDate = this.regDate == null ? LocalDate.now() : this.regDate;
    }

 @Builder
    public Member(Long id, String phone, String pw, String name, String nickname, String birth, LocalDate regDate, LocalDate delDate, MemberRole role, List<Favorite> favorite, List<Report> reports, Authority authority, String profileImgPath, List<MemberChatRoom> memberChatRoom, Blacklist blacklist, AdminTeam adminTeam) {
        this.id = id;
        this.phone = phone;
        this.pw = pw;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.regDate = regDate;
        this.delDate = delDate;
        this.role = role;
        this.favorite = favorite;
        this.reports = reports;
        this.authority = authority;
        this.profileImgPath = profileImgPath;
        this.memberChatRoom = memberChatRoom;
        this.blacklist = blacklist;
        this.adminTeam = adminTeam;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(this.role.toString()));
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

    public void update(String name, String nickname) {
        this.name = name;
        this.nickname = nickname;
    }

    public void updatePw(String pw) {
        this.pw = pw;
    }

    public void updatePhone(String phone){
        this.phone = phone;
    }

    public void updateName(String name){
        this.name = name;
    }

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    public void updateBirth(String birth){
        this.birth = birth;
    }

    public void updateAdminTeam(AdminTeam adminTeam){
        this.adminTeam = adminTeam;
    }

    public void grantRole(MemberRole role){
        this.role = role;
    };

    public void grantAuthorities(Authority authority){
        this.authority = authority;
    };

}
