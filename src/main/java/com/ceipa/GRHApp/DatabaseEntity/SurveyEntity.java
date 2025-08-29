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
@Table(name="survey") // ← quita schema; tu base real es 'railway'
public class SurveyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="date")
    private Date date;

    // La columna real en BD es completed_status
    @Column(name="completed_status")
    private Boolean completedStatus;

    @ManyToOne
    @JoinColumn(name="userId") // ← coincide con la BD actual (INT FK a user.id)
    private UserEntity user;

    @OneToMany(mappedBy="survey")
    @OrderBy("question.id ASC, answer.id ASC")
    private List<SurveyDetailEntity> surveyDetailEntityList;

    // La columna real en BD es diagnostic_id
    @Column(name="diagnostic_id")
    private Integer diagnosticId;

    public SurveyEntity(Date date, Boolean completedStatus, UserEntity user, Integer diagnosticId) {
        this.date = date;
        this.completedStatus = completedStatus;
        this.user = user;
        this.diagnosticId = diagnosticId;
    }
    public SurveyEntity(int id, List<SurveyDetailEntity> surveyDetailEntityList) { this.id = id; this.surveyDetailEntityList = surveyDetailEntityList; }
    public SurveyEntity(int id) { this.id = id; }
    public SurveyEntity(int id, Boolean completedStatus) { this.id = id; this.completedStatus = completedStatus; }
    public SurveyEntity(int id, Boolean completedStatus, Date date, UserEntity user) {
        this.id = id; this.date = date; this.completedStatus = completedStatus; this.user = user;
    }
}
