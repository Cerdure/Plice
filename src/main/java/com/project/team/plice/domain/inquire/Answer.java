package com.project.team.plice.domain.inquire;

import com.project.team.plice.domain.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "answer_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "inquire_id")
    private Inquire inquire;

    @Size(max = 10000)
    private String content;

    private LocalDate regDate;

    @PrePersist
    public void prePersist() {
        this.regDate = this.regDate == null ? LocalDate.now() : this.regDate;
    }

    @Builder
    public Answer(Long id, Inquire inquire, String content, LocalDate regDate) {
        this.id = id;
        this.inquire = inquire;
        this.content = content;
        this.regDate = regDate;
    }
}
