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
public class Authority {

    @Id
    @GeneratedValue
    @Column(name = "authority_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Boolean adminMng;

    private Boolean memberMng;

    private Boolean chatMng;

    private Boolean postMng;

    private Boolean inquiryMng;

    private LocalDateTime modDate;

    @PrePersist
    public void prePersist() {
        this.modDate = LocalDateTime.now();
    }

    @Builder
    public Authority(Long id, Member member, Boolean adminMng, Boolean memberMng, Boolean chatMng, Boolean postMng, Boolean inquiryMng, LocalDateTime modDate) {
        this.id = id;
        this.member = member;
        this.adminMng = adminMng;
        this.memberMng = memberMng;
        this.chatMng = chatMng;
        this.postMng = postMng;
        this.inquiryMng = inquiryMng;
        this.modDate = modDate;
    }

    public void updateAuthorities(Authority authority) {
        this.adminMng = authority.getAdminMng() == null ? false : authority.getAdminMng();
        this.memberMng = authority.getMemberMng() == null ? false : authority.getMemberMng();
        this.chatMng = authority.getChatMng() == null ? false : authority.getChatMng();
        this.postMng = authority.getPostMng() == null ? false : authority.getPostMng();
        this.inquiryMng = authority.getInquiryMng() == null ? false : authority.getInquiryMng();
        this.modDate = LocalDateTime.now();
    }
}
