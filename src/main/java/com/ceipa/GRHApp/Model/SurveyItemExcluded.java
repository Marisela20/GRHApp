package com.ceipa.GRHApp.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyItemExcluded {

    private SurveyItem question;
    private SurveyItem answer;
    private Classification classification;

    public SurveyItemExcluded(SurveyItem question, SurveyItem answer) {
        this.question = question;
        this.answer = answer;
    }
}
