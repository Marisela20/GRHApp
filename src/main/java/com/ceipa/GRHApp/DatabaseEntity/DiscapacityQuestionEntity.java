package com.ceipa.GRHApp.DatabaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "discapacity_question")
public class DiscapacityQuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "question_text", columnDefinition = "TEXT")
    private String questionText;

    @Column(name = "question_type", length = 50)
    private String questionType;

    private Integer section;

    @Column(name = "order_num")
    private int orderNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private DiscapacitySurveyEntity survey;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DiscapacityAnswerOptionEntity> options;

    public DiscapacityQuestionEntity(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DiscapacityQuestionEntity{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", section=" + section +
                ", orderNum=" + orderNum +
                '}';
    }
}
