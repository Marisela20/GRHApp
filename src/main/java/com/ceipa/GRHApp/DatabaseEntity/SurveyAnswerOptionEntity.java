package com.ceipa.GRHApp.DatabaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "survey_answer_options", schema = "grhdatabase") // ajusta el nombre si es diferente
public class SurveyAnswerOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // id de la opción
    private int id;

    @Column(name = "text") // texto de la opción
    private String text;

    @Column(name = "score") // puntuación asociada a esta opción
    private Integer score; // ✅ Esto permite null y evita el error


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_item_id") // clave foránea que apunta a surveyItem.id
    private SurveyItemEntity surveyItem;

    public SurveyAnswerOptionEntity(String text, int score) {
        this.text = text;
        this.score = score;
    }

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
