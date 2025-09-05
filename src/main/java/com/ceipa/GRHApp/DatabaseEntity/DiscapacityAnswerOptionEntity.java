package com.ceipa.GRHApp.DatabaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "discapacity_answer_option")
@Getter
@Setter
@NoArgsConstructor
public class DiscapacityAnswerOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Mantener como VARCHAR. Ajusta length si tu columna tiene otro tamaño.
    @Column(name = "text", nullable = false, length = 255)
    private String text;

    @Column(name = "score")
    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private DiscapacityQuestionEntity question;

    public DiscapacityAnswerOptionEntity(int id) {
        this.id = id;
    }
}
