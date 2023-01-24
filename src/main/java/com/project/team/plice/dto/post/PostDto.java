package com.project.team.plice.dto.post;

import com.project.team.plice.domain.member.Member;
import com.project.team.plice.domain.post.Post;
import com.project.team.plice.domain.post.Reply;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto {

    private Long id;
    private Member member;
    private String memberNickname;
    private String title;
    private String content;
    private Integer hits;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private List<MultipartFile> imageFiles;
    private Integer closed;
    private Integer reFlag;
    private List<Reply> replies;
    private Integer searchBy;
    private String input;

    @Builder
    public PostDto(Long id, Member member, String memberNickname, String title, String content, Integer hits, LocalDateTime regDate, LocalDateTime modDate, List<Reply> replies, Integer searchBy, String input, List<MultipartFile> imageFiles) {
        this.id = id;
        this.member = member;
        this.memberNickname = memberNickname;
        this.title = title;
        this.content = content;
        this.hits = hits;
        this.regDate = regDate;
        this.modDate = modDate;
        this.imageFiles = imageFiles;
        this.replies = replies;
        this.searchBy = searchBy;
        this.input = input;
    }

    public Post toEntity() {
        return Post.builder()
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
