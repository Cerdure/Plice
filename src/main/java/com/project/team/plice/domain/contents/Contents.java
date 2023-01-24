package com.project.team.plice.domain.contents;

import com.project.team.plice.domain.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Contents {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "contents_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String theme;

    @Builder
    public Contents(Long id, Member member, String theme) {
        this.id = id;
        this.member = member;
        this.theme = theme;
    }
}
