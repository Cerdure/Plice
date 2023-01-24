package com.project.team.plice.domain.post;

import com.project.team.plice.domain.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "notice_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String memberNickname;

    private String title;

    @Size(max = 10000)
    private String content;

    private Integer hits;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    @PrePersist
    public void prePersist() {
        this.hits = this.hits == null ? 0 : this.hits;
        this.regDate = this.regDate == null ? LocalDateTime.now() : this.regDate;
    }

    @Builder
    public Notice(Long id, Member member, String memberNickname, String title, String content, LocalDateTime regDate, LocalDateTime modDate, Integer hits) {
        this.id = id;
        this.member = member;
        this.memberNickname = memberNickname;
        this.title = title;
        this.hits = hits;
        this.content = content;
        this.regDate = regDate;
        this.modDate = modDate;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void hitsPlus() {
        this.hits++;
    }
}
