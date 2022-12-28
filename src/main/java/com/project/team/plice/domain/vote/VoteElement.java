package com.project.team.plice.domain.vote;

import com.project.team.plice.domain.member.Member;
import com.project.team.plice.domain.post.Post;
import lombok.*;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class VoteElement {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "voteElement_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id")
    private Vote vote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    @Builder
    public VoteElement(Long id, Vote vote, Member member, String content) {
        this.id = id;
        this.vote = vote;
        this.member = member;
        this.content = content;
    }
}
