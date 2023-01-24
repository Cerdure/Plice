package com.project.team.plice.dto.inquire;

import com.project.team.plice.domain.inquire.Answer;
import com.project.team.plice.domain.inquire.Inquire;
import com.project.team.plice.domain.member.Member;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AnswerDto {

    private Long id;
    private Long inquiryId;
    private Inquire inquire;
    private Member member;
    private String content;
    private LocalDate regDate;

    @Builder
    public AnswerDto(Long id, Long inquiryId, Inquire inquire, Member member, String content, LocalDate regDate) {
        this.id = id;
        this.inquiryId = inquiryId;
        this.inquire = inquire;
        this.member = member;
        this.content = content;
        this.regDate = regDate;
    }

    public Answer toEntity() {
        return Answer.builder()
                .id(this.id)
                .inquire(this.inquire)
                .member(this.member)
                .content(this.content)
                .regDate(this.regDate)
                .build();
    }
}
