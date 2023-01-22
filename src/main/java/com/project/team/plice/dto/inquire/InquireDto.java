package com.project.team.plice.dto.inquire;

import com.project.team.plice.domain.inquire.Answer;
import com.project.team.plice.domain.inquire.Inquire;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.member.MemberDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class InquireDto {

    private Long id;
    private Member member;
    private String title;
    private String content;
    private String type;
    private Integer isAnswered;  // 답변 여부 (0,1)
    private LocalDateTime regDate;
    private Answer answer;

    @Builder
    public InquireDto(Long id, Member member, String title, String content, String type, Integer isAnswered, LocalDateTime regDate, Answer answer) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.content = content;
        this.type = type;
        this.isAnswered = isAnswered;
        this.regDate = regDate;
        this.answer = answer;
    }

    public Inquire toEntity(){
        return Inquire.builder()
                .id(this.id)
                .member(this.member)
                .title(this.title)
                .content(this.content)
                .type(this.type)
                .isAnswered(this.isAnswered)
                .regDate(this.regDate)
                .answer(this.answer)
                .build();
    }
}
