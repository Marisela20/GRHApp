package com.ceipa.GRHApp.DatabaseEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@lombok.Getter
@lombok.Setter
@Entity
@Table(
        name = "discapacity_response",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_resp_draft",
                        columnNames = {"survey_id", "question_id", "user_id", "section", "is_draft"}
                )
        }
)
public class DiscapacityResponseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "survey_id", nullable = false)
    private DiscapacitySurveyEntity survey;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private DiscapacityQuestionEntity question;

    @ManyToOne
    @JoinColumn(name = "answer_option_id")
    private DiscapacityAnswerOptionEntity answerOption;

    @Column(name = "manual_text")
    private String manualText;

    // Quitar @Lob y columnDefinition para que mapee a VARCHAR (como está en tu BD)
    // Si tu columna tiene un tamaño definido (p.ej. 255, 1024), puedes añadir length = ...
    @Column(name = "free_text")
    private String freeText;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userAccount;

    @Column(name = "created_at")
    private Timestamp createdAt;

    // Campo histórico (si se usa aún, se mantiene)
    @ManyToOne
    @JoinColumn(name = "answer_id")
    private DiscapacityAnswerOptionEntity answer;

    @Column(name = "is_draft", nullable = false)
    private Boolean isDraft = Boolean.TRUE;

    @Column(name = "section", nullable = false)
    private Integer section = 1;

    // Getters/Setters explícitos opcionales (Lombok ya los genera)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public DiscapacitySurveyEntity getSurvey() { return survey; }
    public void setSurvey(DiscapacitySurveyEntity survey) { this.survey = survey; }

    public DiscapacityQuestionEntity getQuestion() { return question; }
    public void setQuestion(DiscapacityQuestionEntity question) { this.question = question; }

    public DiscapacityAnswerOptionEntity getAnswerOption() { return answerOption; }
    public void setAnswerOption(DiscapacityAnswerOptionEntity answerOption) { this.answerOption = answerOption; }

    public String getManualText() { return manualText; }
    public void setManualText(String manualText) { this.manualText = manualText; }

    public String getFreeText() { return freeText; }
    public void setFreeText(String freeText) { this.freeText = freeText; }

    public UserEntity getUserAccount() { return userAccount; }
    public void setUserAccount(UserEntity userAccount) { this.userAccount = userAccount; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public DiscapacityAnswerOptionEntity getAnswer() { return answer; }
    public void setAnswer(DiscapacityAnswerOptionEntity answer) { this.answer = answer; }

    public Boolean getIsDraft() { return isDraft; }
    public void setIsDraft(Boolean isDraft) { this.isDraft = isDraft; }

    public Integer getSection() { return section; }
    public void setSection(Integer section) { this.section = section; }
}
