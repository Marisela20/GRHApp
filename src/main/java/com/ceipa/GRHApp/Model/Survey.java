package com.ceipa.GRHApp.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Survey {

    private int id;
    private Date date;
    private Date createdAt;
    private Boolean completedStatus;
    private UserAccount userAccount;
    private List<SurveyDetail> surveyDetailList = new ArrayList<>();
    private Integer diagnosticId;
    private String name;
    private String description;
    private String title;
    private Integer userId;

    // 🔹 Campo necesario para setQuestions(...) con preguntas de discapacidad
    private List<DiscapacityQuestion> questions;

    // Constructor básico por ID
    public Survey(int id) {
        this.id = id;
        this.surveyDetailList = new ArrayList<>();
    }

    // Constructor para creación (sin ID)
    public Survey(Date date, UserAccount userAccount, Boolean completedStatus, Integer diagnosticId) {
        this.date = date;
        this.userAccount = userAccount;
        this.completedStatus = completedStatus;
        this.diagnosticId = diagnosticId;
        this.surveyDetailList = new ArrayList<>();
    }

    // Constructor básico con ID y datos principales
    public Survey(int id, Date date, Boolean completedStatus, UserAccount userAccount, Integer diagnosticId) {
        this.id = id;
        this.date = date;
        this.completedStatus = completedStatus;
        this.userAccount = userAccount;
        this.diagnosticId = diagnosticId;
        this.surveyDetailList = new ArrayList<>();
    }

    // Constructor completo
    public Survey(int id, Date date, Boolean completedStatus, UserAccount userAccount,
                  List<SurveyDetail> surveyDetailList, Integer diagnosticId,
                  String name, String description, Integer userId) {
        this.id = id;
        this.date = date;
        this.completedStatus = completedStatus;
        this.userAccount = userAccount;
        this.surveyDetailList = surveyDetailList != null ? surveyDetailList : new ArrayList<>();
        this.diagnosticId = diagnosticId;
        this.name = name;
        this.description = description;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "id=" + id +
                ", date=" + date +
                ", createdAt=" + createdAt +
                ", completedStatus=" + completedStatus +
                ", userAccount=" + userAccount +
                ", diagnosticId=" + diagnosticId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", surveyDetailList=" + surveyDetailList +
                ", questions=" + (questions != null ? questions.size() : 0) +
                '}';
    }
}
