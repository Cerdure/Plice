package com.project.team.plice.domain.admin;

import com.project.team.plice.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ReportAnswer {

    @Id @GeneratedValue
    @Column(name = "report_answer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private Report report;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Member admin;

    private String content;

    private LocalDateTime regDate;

    @PrePersist
    public void prePersist() {
        this.regDate = this.regDate == null ? LocalDateTime.now() : this.regDate;
    }

    @Builder
    public ReportAnswer(Long id, Report report, Member admin, String content, LocalDateTime regDate) {
        this.id = id;
        this.report = report;
        this.admin = admin;
        this.content = content;
        this.regDate = regDate;
    }
}
