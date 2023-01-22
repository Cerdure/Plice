package com.project.team.plice.domain.inquire;

import com.project.team.plice.domain.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquire {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "inquire_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    @Size(max=10000)
    private String content;

    private String type;

    private Integer isAnswered;  // 답변 여부 (0,1)

    private LocalDateTime regDate;

    @OneToOne(mappedBy = "inquire")
    private Answer answer;

    @PrePersist
    public void prePersist() {
        this.regDate = this.regDate == null ? LocalDateTime.now() : this.regDate;
        this.isAnswered = this.isAnswered == null ? 0 : this.isAnswered;
    }

    @Builder
    public Inquire(Long id, Member member, String title, String content, String type, Integer isAnswered, LocalDateTime regDate, Answer answer) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.content = content;
        this.type = type;
        this.isAnswered = isAnswered;
        this.regDate = regDate;
        this.answer = answer;
    }

    public void changeIsAnswered(Integer isAnswered){
        this.isAnswered = isAnswered;
    }

    public void changeInquire(String content,String title, String type) {
        this.title = title;
        this.content = content;
        this.type = type;
    }
}
