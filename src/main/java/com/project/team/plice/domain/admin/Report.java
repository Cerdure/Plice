package com.project.team.plice.domain.admin;

import com.project.team.plice.domain.chat.Chat;
import com.project.team.plice.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Report {

    @Id @GeneratedValue
    @Column(name = "report_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private Member reporter;

    private String reason;

    private LocalDateTime regDate;

    @OneToMany(mappedBy = "report")
    private List<ReportAnswer> reportAnswers;

    @PrePersist
    public void prePersist() {
        this.regDate = this.regDate == null ? LocalDateTime.now() : this.regDate;
    }

    @Builder
    public Report(Long id, Chat chat, Member reporter, String reason, LocalDateTime regDate, List<ReportAnswer> reportAnswers) {
        this.id = id;
        this.chat = chat;
        this.reporter = reporter;
        this.reason = reason;
        this.regDate = regDate;
        this.reportAnswers = reportAnswers;
    }
}
