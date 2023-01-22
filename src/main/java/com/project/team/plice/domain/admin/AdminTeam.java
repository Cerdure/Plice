package com.project.team.plice.domain.admin;

import com.project.team.plice.domain.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminTeam {

    @Id @GeneratedValue
    @Column(name = "admin_team_id")
    private Long id;

    @OneToMany(mappedBy = "adminTeam")
    private List<Member> members;

    private String name;

    @Builder
    public AdminTeam(Long id, List<Member> members, String name) {
        this.id = id;
        this.members = members;
        this.name = name;
    }
}
