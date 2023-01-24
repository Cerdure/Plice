package com.project.team.plice.domain.admin;

import com.project.team.plice.domain.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Blacklist {

    @Id
    @GeneratedValue
    @Column(name = "black_list_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ip_id")
    private IP ip;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String reason;

    private LocalDateTime regDate;

    private LocalDateTime expDate;

    @PrePersist
    public void prePersist() {
        this.regDate = this.regDate == null ? LocalDateTime.now() : this.regDate;
    }

    @Builder
    public Blacklist(Long id, IP ip, Member member, String reason, LocalDateTime regDate, LocalDateTime expDate) {
        this.id = id;
        this.ip = ip;
        this.member = member;
        this.reason = reason;
        this.regDate = regDate;
        this.expDate = expDate;
    }
}
