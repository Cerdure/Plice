package com.project.team.plice.domain.admin;

import com.project.team.plice.domain.member.Member;
import lombok.*;
import org.apache.xpath.operations.Bool;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Authority {

    @Id @GeneratedValue
    @Column(name = "authority_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Boolean adminMng;

    private Boolean memberMng;

    private Boolean chatMng;

    private Boolean postMng;

    private Boolean noticeMng;

    private LocalDateTime modDate;

    @PrePersist
    public void prePersist() {
        this.modDate = LocalDateTime.now();
    }

    @Builder
    public Authority(Long id, Member member, Boolean adminMng, Boolean memberMng, Boolean chatMng, Boolean postMng, Boolean noticeMng, LocalDateTime modDate) {
        this.id = id;
        this.member = member;
        this.adminMng = adminMng;
        this.memberMng = memberMng;
        this.chatMng = chatMng;
        this.postMng = postMng;
        this.noticeMng = noticeMng;
        this.modDate = modDate;
    }
}
