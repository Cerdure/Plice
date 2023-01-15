package com.project.team.plice.domain.member;

import com.project.team.plice.domain.data.ApartData;
import lombok.*;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Favorite {

    @Id @GeneratedValue
    @Column(name = "favorite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apart_id")
    private ApartData apartData;

    @Builder
    public Favorite(Long id, Member member, ApartData apartData) {
        this.id = id;
        this.member = member;
        this.apartData = apartData;
    }
}
