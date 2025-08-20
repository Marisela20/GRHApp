package com.ceipa.GRHApp.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SurveyDetail {

    private Integer surveyId;
    private Integer questionId;
    private Integer subQuestionId;
    private Integer answerId;
    private Integer subAnswerId;

    // ✅ Campo agregado para preguntas abiertas
    private String freeText;

    private SurveyItem question;
    private SurveyItem answer;
    private SurveyItem subQuestion;
    private SurveyItem subAnswer;

    public SurveyDetail(SurveyItem question, SurveyItem answer, SurveyItem subQuestion, SurveyItem subAnswer) {
        this.question = question;
        this.answer = answer;
        this.subQuestion = subQuestion;
        this.subAnswer = subAnswer;
    }

    @Override
    public String toString() {
        return "SurveyDetail{" +
                "question=" + (question != null ? question.getId() : null) +
                ", answer=" + (answer != null ? answer.getId() : null) +
                ", freeText=" + freeText +
                '}';
    }
}
