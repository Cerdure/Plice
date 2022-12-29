package com.project.team.plice.dto.post;

import com.project.team.plice.domain.member.Member;
import com.project.team.plice.domain.post.Notice;
import com.project.team.plice.domain.post.Post;
import com.project.team.plice.domain.post.Reply;
import com.project.team.plice.domain.post.UploadFile;
import com.project.team.plice.dto.member.MemberDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Builder
    public PostDto(Long id, Member member, String memberNickname, String title, String content, Integer hits, LocalDateTime regDate, LocalDateTime modDate, List<MultipartFile> imageFiles) {
        this.id = id;
        this.member = member;
        this.memberNickname = memberNickname;
        this.title = title;
        this.content = content;
        this.hits = hits;
        this.regDate = regDate;
        this.modDate = modDate;
        this.imageFiles = imageFiles;
    }

    public Post toEntity(){
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
