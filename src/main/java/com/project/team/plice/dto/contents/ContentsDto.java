package com.project.team.plice.dto.contents;

import com.project.team.plice.domain.contents.Contents;
import com.project.team.plice.domain.member.Member;
import lombok.Builder;
import lombok.Data;

@Data
public class ContentsDto {

    private Long id;
    private Member member;
    private String theme;

    @Builder
    public ContentsDto(Long id, Member member, String theme) {
        this.id = id;
        this.member = member;
        this.theme = theme;
    }

    public Contents toEntity() {
        return Contents.builder()
                .id(this.id)
                .member(this.member)
                .theme(this.theme)
                .build();
    }
}
