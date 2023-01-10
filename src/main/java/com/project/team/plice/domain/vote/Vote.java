package com.project.team.plice.domain.vote;

import com.project.team.plice.domain.data.ApartData;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Vote {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vote_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apart_id")
    private ApartData apartData;

    private Integer memberCount;

    private LocalDateTime regDate;

    private LocalDateTime deadLine;

    @PrePersist
    public void prePersist() {
        this.regDate = this.regDate == null ? LocalDateTime.now() : this.regDate;
    }

    @Builder
    public Vote(Long id, ApartData apartData, Integer memberCount, LocalDateTime regDate, LocalDateTime deadLine) {
        this.id = id;
        this.apartData = apartData;
        this.memberCount = memberCount;
        this.regDate = regDate;
        this.deadLine = deadLine;
    }
}
