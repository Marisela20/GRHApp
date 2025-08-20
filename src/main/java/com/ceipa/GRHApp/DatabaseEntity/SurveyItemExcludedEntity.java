package com.ceipa.GRHApp.DatabaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="surveyItemExcluded", schema="grhdatabase")
public class SurveyItemExcludedEntity {


    @EmbeddedId
    private SurveyItemExcludedKey key;

    @ManyToOne
    @MapsId("surveyItem")
    @JoinColumn(name="questionId")
    private SurveyItemEntity question;

    @ManyToOne
    @MapsId("surveyItem")
    @JoinColumn(name="answerId")
    private SurveyItemEntity answer;

    @ManyToOne
    @MapsId("classification")
    @JoinColumn(name="classificationId")
    private ClassificationEntity classification;

    public SurveyItemExcludedEntity(SurveyItemEntity question, SurveyItemEntity answer, ClassificationEntity classification) {

        this.question = question;
        this.answer = answer;
        this.classification = classification;
        this.key = new SurveyItemExcludedKey();
        this.key.setAnswer(answer.getId());
        this.key.setQuestion(question.getId());
    }
}
