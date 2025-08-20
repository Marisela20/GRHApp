package com.ceipa.GRHApp.Model;

import java.util.List;

public class DiscapacityQuestion {

    private Integer id;
    private String description;
    private Integer section;
    private Integer order;
    private String questionType;
    private Integer surveyId;
    private List<DiscapacityAnswerOption> answerOptions;

    // ✅ Nuevo: respuestas seleccionadas del usuario
    private List<DiscapacityAnswerOption> selectedOptions;

    public DiscapacityQuestion() {
    }

    public DiscapacityQuestion(Integer id, String description, Integer section, Integer order,
                               String questionType, Integer surveyId,
                               List<DiscapacityAnswerOption> answerOptions,
                               List<DiscapacityAnswerOption> selectedOptions) {
        this.id = id;
        this.description = description;
        this.section = section;
        this.order = order;
        this.questionType = questionType;
        this.surveyId = surveyId;
        this.answerOptions = answerOptions;
        this.selectedOptions = selectedOptions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public List<DiscapacityAnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<DiscapacityAnswerOption> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public List<DiscapacityAnswerOption> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<DiscapacityAnswerOption> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }
}
