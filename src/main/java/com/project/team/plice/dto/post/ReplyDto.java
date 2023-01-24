package com.project.team.plice.dto.post;

import com.project.team.plice.domain.member.Member;
import com.project.team.plice.domain.post.Post;
import com.project.team.plice.domain.post.Reply;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyDto {

    private Long id;
    private Post post;
    private Member member;
    private LocalDateTime regDate;
    private String content;
    private Long postId;
    private Long replyId;
    private Long parentId;
    private Reply parent;
    private Integer level;

    @Builder
    public ReplyDto(Long id, Post post, Member member, LocalDateTime regDate, String content, Long postId, Long replyId, Long parentId, Reply parent, Integer level) {
        this.id = id;
        this.post = post;
        this.member = member;
        this.regDate = regDate;
        this.content = content;
        this.postId = postId;
        this.replyId = replyId;
        this.parentId = parentId;
        this.parent = parent;
        this.level = level;
    }

    public Reply toEntity() {
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
