package com.project.team.plice.dto.inquire;

import com.project.team.plice.domain.chat.ChatRoom;
import com.project.team.plice.domain.inquire.Answer;
import com.project.team.plice.domain.inquire.Inquire;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AnswerDto {

    private Long id;
    private Inquire inquire;
    private String content;
    private LocalDate regDate;

    @Builder
    public AnswerDto(Long id, Inquire inquire, String content, LocalDate regDate) {
        this.id = id;
        this.inquire = inquire;
        this.content = content;
        this.regDate = regDate;
    }

    public Answer toEntity(){
        return Answer.builder()
                .id(this.id)
                .inquire(this.inquire)
                .content(this.content)
                .regDate(this.regDate)
                .build();
    }
}
