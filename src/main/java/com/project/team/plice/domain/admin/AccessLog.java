package com.project.team.plice.domain.admin;

import com.project.team.plice.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class AccessLog {

    @Id
    @GeneratedValue
    @Column(name = "access_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String uri;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IP_id")
    private IP ip;

    private LocalDateTime regDate;

    @PrePersist
    public void prePersist() {
        this.regDate = this.regDate == null ? LocalDateTime.now() : this.regDate;
    }

    @Builder
    public AccessLog(Long id, Member member, String uri, IP ip, LocalDateTime regDate) {
        this.id = id;
        this.member = member;
        this.uri = uri;
        this.ip = ip;
        this.regDate = regDate;
    }
}
