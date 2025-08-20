package com.ceipa.GRHApp.DatabaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class SurveyDetailEntityKey implements Serializable {

    @Column(name="survey_id")
    private int surveyId;

    @Column(name="question_id")
    private int questionId;

    @Column(name="sub_question_id")
    private int subQuestionId;

}
