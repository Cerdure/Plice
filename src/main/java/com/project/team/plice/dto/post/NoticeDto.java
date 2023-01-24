package com.project.team.plice.dto.post;

import com.project.team.plice.domain.member.Member;
import com.project.team.plice.domain.post.Notice;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticeDto {

    private Long id;
    private Member member;
    private String memberNickname;
    private String title;
    private String content;
    private Integer hits;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    @Builder
    public NoticeDto(Long id, Member member, String memberNickname, String title, String content, Integer hits, LocalDateTime regDate, LocalDateTime modDate) {
        this.id = id;
        this.member = member;
        this.memberNickname = memberNickname;
        this.title = title;
        this.content = content;
        this.hits = hits;
        this.regDate = regDate;
        this.modDate = modDate;
    }

    public Notice toEntity() {
        return Notice.builder()
                .id(this.id)
                .member(this.member)
                .memberNickname(this.memberNickname)
                .title(this.title)
                .content(this.content)
                .hits(this.hits)
                .regDate(this.regDate)
                .modDate(this.modDate)
                .build();
    }
}
