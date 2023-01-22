package com.project.team.plice.domain.admin;

import com.project.team.plice.domain.chat.Chat;
import com.project.team.plice.domain.member.Member;
import lombok.*;
import org.apache.xpath.operations.Bool;

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

    private Boolean complete;

    @PrePersist
    public void prePersist() {
        this.regDate = this.regDate == null ? LocalDateTime.now() : this.regDate;
        this.complete = this.complete == null ? false : this.complete;
    }

    @Builder
    public Report(Long id, Chat chat, Member reporter, String reason, LocalDateTime regDate, Boolean complete) {
        this.id = id;
        this.chat = chat;
        this.reporter = reporter;
        this.reason = reason;
        this.regDate = regDate;
        this.complete = complete;
    }

    public void changeComplete(Boolean state){
        this.complete = state;
    }
}
