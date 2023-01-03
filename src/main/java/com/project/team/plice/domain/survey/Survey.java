package com.project.team.plice.domain.survey;

import com.project.team.plice.domain.Apart;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Survey {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "survey_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apart_id")
    private Apart apart;

    private Integer memberCount;

    private LocalDateTime regDate;

    private LocalDateTime deadLine;

    @PrePersist
    public void prePersist() {
        this.regDate = this.regDate == null ? LocalDateTime.now() : this.regDate;
    }

    @Builder
    public Survey(Long id, Apart apart, Integer memberCount, LocalDateTime regDate, LocalDateTime deadLine) {
        this.id = id;
        this.apart = apart;
        this.memberCount = memberCount;
        this.regDate = regDate;
        this.deadLine = deadLine;
    }
}
