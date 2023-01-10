package com.project.team.plice.domain.survey;

import com.project.team.plice.domain.member.Member;
import com.project.team.plice.domain.vote.Vote;
import lombok.*;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class SurveyElement {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "surveyElement_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    private Integer selectValue;

    @Builder
    public SurveyElement(Long id, Survey survey, Member member, String content, Integer selectValue) {
        this.id = id;
        this.survey = survey;
        this.member = member;
        this.content = content;
        this.selectValue = selectValue;
    }
}
