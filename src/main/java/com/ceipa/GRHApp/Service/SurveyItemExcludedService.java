package com.ceipa.GRHApp.Service;

import com.ceipa.GRHApp.Model.SurveyItemExcluded;

import java.util.List;

public interface SurveyItemExcludedService {

    List<SurveyItemExcluded> getSurveyItemExcludedList();

    List<SurveyItemExcluded> getSurveyItemExcludedListByQuestionId(int questionId);
}
