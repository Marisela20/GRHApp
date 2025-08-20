package com.ceipa.GRHApp.Service;

import com.ceipa.GRHApp.Model.SurveyItem;

import java.util.Collection;
import java.util.List;

public interface SurveyItemservice {

    List<SurveyItem> getSurveyItemListByClassification(int classification);

    List<SurveyItem> getSurveyItemListById(int surveyItemId);

    List<SurveyItem> getSurveyItemQuestionListByClassification(int classification);

    List<SurveyItem> getSurveyItemQuestionList(int diagnosticId);

    List<SurveyItem> getSurveyItemResponseSectionTwo();

    List<SurveyItem> getSurveyItemListByClassificationAndFatherId(int classification, int fatherId);

    List<SurveyItem> getSurveyItemAnswerListByClassificationId(int classification);
}
