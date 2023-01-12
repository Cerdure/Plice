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

<<<<<<< HEAD
    private Integer selects;

    @Builder
    public SurveyElement(Long id, Survey survey, Member member, String content, Integer selects) {
=======
    private Integer selectValue;

    @Builder
    public SurveyElement(Long id, Survey survey, Member member, String content, Integer selectValue) {
>>>>>>> 65c8704fa6ac02431ad37b65f159cdf02b74b094
        this.id = id;
        this.survey = survey;
        this.member = member;
        this.content = content;
<<<<<<< HEAD
        this.selects = selects;
=======
        this.selectValue = selectValue;
>>>>>>> 65c8704fa6ac02431ad37b65f159cdf02b74b094
    }
}
