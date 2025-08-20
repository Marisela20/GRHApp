package com.ceipa.GRHApp.DatabaseEntity;

import com.ceipa.GRHApp.DatabaseEntity.SurveyDetailEntity;
import com.ceipa.GRHApp.DatabaseEntity.UserEntity;
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
@Table(name="survey", schema="grhdatabase")
public class SurveyEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="date")
    private Date date;

    @Column(name="completedStatus")
    private Boolean completedStatus;

    @ManyToOne
    @JoinColumn(name="userId")
    private UserEntity user;

    @OneToMany(mappedBy="survey")
    @OrderBy("question.id ASC, answer.id ASC")
    private List<SurveyDetailEntity> surveyDetailEntityList;

    @Column(name="diagnosticId")
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

    public SurveyEntity(int id) {
        this.id = id;
    }

    public SurveyEntity(int id, Boolean completedStatus) {
        this.id = id;
        this.completedStatus = completedStatus;
    }

    public SurveyEntity(int id, Boolean completedStatus,  Date date, UserEntity user) {
        this.id = id;
        this.date = date;
        this.completedStatus = completedStatus;
        this.user = user;
    }
}
