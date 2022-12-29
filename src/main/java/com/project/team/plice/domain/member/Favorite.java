package com.project.team.plice.domain.member;

import com.project.team.plice.domain.Apart;
import lombok.*;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Favorite {

    @Id @GeneratedValue
    @Column(name = "favorite_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apart_id")
    private Apart apart;

    @Builder
    public Favorite(Long id, Member member, Apart apart) {
        this.id = id;
        this.member = member;
        this.apart = apart;
    }
}
