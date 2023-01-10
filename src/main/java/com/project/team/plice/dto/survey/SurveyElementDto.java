package com.project.team.plice.dto.survey;

import com.project.team.plice.domain.member.Member;
import com.project.team.plice.domain.survey.Survey;
import com.project.team.plice.domain.survey.SurveyElement;
import lombok.Builder;
import lombok.Data;

@Data
public class SurveyElementDto {

    private Long id;
    private Survey survey;
    private Member member;
    private String content;
<<<<<<< HEAD
    private Integer selects;

    @Builder
    public SurveyElementDto(Long id, Survey survey, Member member, String content, Integer selects) {
=======
    private Integer selectValue;

    @Builder
    public SurveyElementDto(Long id, Survey survey, Member member, String content, Integer selectValue) {
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

    public SurveyElement toEntity(){
        return SurveyElement.builder()
                .id(this.id)
                .survey(this.survey)
                .member(this.member)
                .content(this.content)
<<<<<<< HEAD
                .selects(this.selects)
=======
                .selectValue(this.selectValue)
>>>>>>> 65c8704fa6ac02431ad37b65f159cdf02b74b094
                .build();
    }
}
