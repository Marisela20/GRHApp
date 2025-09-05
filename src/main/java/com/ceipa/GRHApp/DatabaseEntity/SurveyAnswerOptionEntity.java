package com.ceipa.GRHApp.DatabaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "survey_answer_options")
public class SurveyAnswerOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "text", nullable = false, length = 500)
    private String text;

    @Column(name = "score")
    private Integer score;

    // FK correcta según tu tabla: survey_item_id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "survey_item_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_sao_survey_item")
    )
    private SurveyItemEntity surveyItem;

    public SurveyAnswerOptionEntity(String text, int score) {
        this.text = text;
        this.score = score;
    }

    // ==== Compat: mantiene API "question" usada en servicios/plantillas ====
    @Transient
    public SurveyItemEntity getQuestion() {
        return this.surveyItem;
    }

    public void setQuestion(SurveyItemEntity question) {
        this.surveyItem = question;
    }
    // ======================================================================

    @Override
    public String toString() {
        return "SurveyAnswerOptionEntity{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", score=" + score +
                ", surveyItem=" + (surveyItem != null ? surveyItem.getId() : "null") +
                '}';
    }
}
