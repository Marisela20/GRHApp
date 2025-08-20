package com.ceipa.GRHApp.DatabaseEntity;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "survey_item", schema = "grhdatabase")
public class SurveyItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_question")
    private Boolean isQuestion;

    private Integer section;

    @Column(name = "is_sublevel")
    private Boolean isSublevel;

    private Integer score;

    @Column(name = "order_column")
    private Integer order;

    @OneToOne
    @JoinColumn(name = "father_id")
    private SurveyItemEntity father;

    @OneToOne
    @JoinColumn(name = "classification_id")
    private ClassificationEntity classification;

    @Column(name = "diagnostic_id")
    private Integer diagnosticId;

    @OneToMany(mappedBy = "surveyItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SurveyAnswerOptionEntity> surveyAnswerOptions;

    public SurveyItemEntity(Integer id) {
        this.id = id;
    }
}
