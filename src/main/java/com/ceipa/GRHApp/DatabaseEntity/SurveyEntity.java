package com.ceipa.GRHApp.DatabaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "survey")
public class SurveyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="date")
    private Date date;

    // columna real: completed_status (BOOLEAN / TINYINT en MySQL)
    @Column(name="completed_status")
    private Boolean completedStatus;

    // FK a user.id (columna en BD: userId). Si tu columna fuera 'user_id', cámbiala aquí.
    @ManyToOne
    @JoinColumn(name="userId")
    private UserEntity user;

    // Relación con detalles (ajusta el 'mappedBy' al nombre exacto del campo en SurveyDetailEntity)
    @OneToMany(mappedBy="survey")
    @OrderBy("question.id ASC, answer.id ASC")
    private List<SurveyDetailEntity> surveyDetailEntityList;

    // columna real: diagnostic_id
    @Column(name="diagnostic_id")
    private Integer diagnosticId;

    public SurveyEntity(Date date, Boolean completedStatus, UserEntity user, Integer diagnosticId) {
        this.date = date;
        this.completedStatus = completedStatus;
        this.user = user;
        this.diagnosticId = diagnosticId;
    }

    public SurveyEntity(int id, List<SurveyDetailEntity> surveyDetailEntityList) {
        this.id = id;
        this.surveyDetailEntityList = surveyDetailEntityList;
    }

    public SurveyEntity(int id) { this.id = id; }

    public SurveyEntity(int id, Boolean completedStatus) {
        this.id = id;
        this.completedStatus = completedStatus;
    }

    public SurveyEntity(int id, Boolean completedStatus, Date date, UserEntity user) {
        this.id = id;
        this.date = date;
        this.completedStatus = completedStatus;
        this.user = user;
    }
}
