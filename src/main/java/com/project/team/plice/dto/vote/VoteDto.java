package com.project.team.plice.dto.vote;

import com.project.team.plice.domain.data.ApartData;
import com.project.team.plice.domain.vote.Vote;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VoteDto {

    private Long id;
    private ApartData apartData;
    private Integer memberCount;
    private LocalDateTime regDate;
    private LocalDateTime deadLine;

    @Builder
    public VoteDto(Long id, ApartData apartData, Integer memberCount, LocalDateTime regDate, LocalDateTime deadLine) {
        this.id = id;
        this.apartData = apartData;
        this.memberCount = memberCount;
        this.regDate = regDate;
        this.deadLine = deadLine;
    }

    public Vote toEntity(){
        return Vote.builder()
                .id(this.id)
                .apartData(this.apartData)
                .memberCount(this.memberCount)
                .regDate(this.regDate)
                .deadLine(this.deadLine)
                .build();
    }
}
