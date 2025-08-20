package com.ceipa.GRHApp.DatabaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name="surveyDetail", schema="grhdatabase")
public class SurveyDetailEntity implements Serializable {

    @EmbeddedId
    private SurveyDetailEntityKey key;

    @ManyToOne
    @MapsId("survey")
    @JoinColumn(name="survey_id", nullable = false)
    private SurveyEntity survey;

    @ManyToOne
    @JoinColumn(name="question_id", nullable = false, insertable = false, updatable = false)
    private SurveyItemEntity question;

    @ManyToOne
    @JoinColumn(name="answer_id")
    private SurveyItemEntity answer;

    @ManyToOne
    @JoinColumn(name="sub_question_id", nullable = false, insertable = false, updatable = false)
    private SurveyItemEntity subQuestion;

    @ManyToOne
    @JoinColumn(name="sub_answer_id")
    private SurveyItemEntity subAnswer;

    public SurveyDetailEntity(SurveyEntity survey, SurveyItemEntity question, SurveyItemEntity answer, SurveyItemEntity subQuestion, SurveyItemEntity subAnswer) {
        this.survey = survey;
        this.question = question;
        this.answer = answer;
        this.subQuestion = subQuestion;
        this.subAnswer = subAnswer;
        this.key = new SurveyDetailEntityKey();
        this.key.setSurveyId(survey.getId());
        this.key.setQuestionId(question.getId());
        this.key.setSubQuestionId(subQuestion.getId());
    }

    public SurveyDetailEntity() {
        this.key = new SurveyDetailEntityKey();
    }

    public SurveyDetailEntity(SurveyEntity survey) {
        this.survey = survey;
    }

    public SurveyEntity getSurvey() {
        return survey;
    }

    public void setSurvey(SurveyEntity survey) {
        this.survey = survey;
        this.key.setSurveyId(survey.getId());
    }

    public SurveyItemEntity getQuestion() {
        return question;
    }

    public void setQuestion(SurveyItemEntity question) {
        this.question = question;
        this.key.setQuestionId(question.getId());
    }

    public SurveyItemEntity getSubQuestion() {
        return subQuestion;
    }

    public void setSubQuestion(SurveyItemEntity subQuestion) {
        this.subQuestion = subQuestion;
        this.key.setSubQuestionId(subQuestion.getId());
    }

    public SurveyItemEntity getAnswer() {
        return answer;
    }

    public void setAnswer(SurveyItemEntity answer) {
        this.answer = answer;
    }
}
