package com.ceipa.GRHApp.Model;

import lombok.Data;

import java.util.List;

@Data
public class DiscapacitySurveyItem {
    private int id;
    private int section;
    private int order;
    private String description;
    private List<DiscapacityAnswerOption> answerOptions;
}
