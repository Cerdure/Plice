package com.project.team.plice.repository.survey;

import com.project.team.plice.domain.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
