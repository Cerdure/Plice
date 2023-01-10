package com.project.team.plice.dto.vote;

import com.project.team.plice.domain.member.Member;
import com.project.team.plice.domain.vote.Vote;
import com.project.team.plice.domain.vote.VoteElement;
import lombok.Builder;
import lombok.Data;

@Data
public class VoteElementDto {

    private Long id;
    private Vote vote;
    private Member member;
    private String content;

    @Builder
    public VoteElementDto(Long id, Vote vote, Member member, String content) {
        this.id = id;
        this.vote = vote;
        this.member = member;
        this.content = content;
    }

    public VoteElement toEntity(){
        return VoteElement.builder()
                .id(this.id)
                .vote(this.vote)
                .member(this.member)
                .content(this.content)
                .build();
    }
}
