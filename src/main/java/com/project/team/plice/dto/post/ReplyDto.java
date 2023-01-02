package com.project.team.plice.dto.post;

import com.project.team.plice.domain.member.Member;
import com.project.team.plice.domain.post.Post;
import com.project.team.plice.domain.post.Reply;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReplyDto {

    private Long id;
    private Post post;
    private Member member;
    private LocalDateTime regDate;
    private String content;
    private Long replyId;
    private Reply parent;
    private Integer level;
    private Long parentId;

    @Builder
    public ReplyDto(Long id, Post post, Member member, LocalDateTime regDate, String content, Long replyId, Reply parent, Integer level, Long parentId) {
        this.id = id;
        this.post = post;
        this.member = member;
        this.regDate = regDate;
        this.content = content;
        this.replyId = replyId;
        this.parent = parent;
        this.level = level;
        this.parentId = parentId;
    }

    public Reply toEntity(){
        return Reply.builder()
                .id(this.id)
                .post(this.post)
                .member(this.member)
                .regDate(this.regDate)
                .content(this.content)
                .parent(this.parent)
                .level(this.level)
                .build();
    }
}
