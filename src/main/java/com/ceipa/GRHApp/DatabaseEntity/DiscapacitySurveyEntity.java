package com.ceipa.GRHApp.DatabaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "discapacity_survey")
@Getter
@Setter
@NoArgsConstructor
public class DiscapacitySurveyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    // ✅ CORREGIDO: nombre correcto de la columna
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    private UserEntity userAccount;

    @Column(name = "created_at")
    private Date createdAt;

    private String title;

    private Boolean completedStatus;

    @Column(name = "diagnostic_id")
    private Integer diagnosticId;

    @Column(name = "current_section")
    private Integer currentSection;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DiscapacityQuestionEntity> questions;

    public DiscapacitySurveyEntity(int id) {
        this.id = id;
    }
}
