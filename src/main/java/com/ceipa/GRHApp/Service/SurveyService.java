package com.ceipa.GRHApp.Service;

import com.ceipa.GRHApp.DatabaseEntity.UserEntity;
import com.ceipa.GRHApp.Model.*;

import java.util.List;
import java.util.Map;

public interface SurveyService {

    Survey getSurvey(int diagnosticId);

    Survey getSurveyById(int surveyId);

    List<Survey> getSurveyByDiagnosticId(int diagnosticId); // ✅ CORREGIDO: retorna List<Survey>

    List<SurveyItem> getSurveyItems(Survey survey, boolean next, int questionId, int diagnosticId);

    int saveSurvey(Survey survey);

    int closeSurvey(int surveyId);

    List<SurveyItem> getSurveyItemsForDiscapacityByUser(UserEntity user, int section);

    void updateSurvey(Survey survey);

    void saveDiscapacitySurveyDetail(Map<String, String[]> parameterMap, int surveyId);

    List<Survey> getAllSurveys();

    void saveDiscapacityManualAnswer(int surveyId, int questionId, String freeText);

    void saveSurveyDetail(int surveyId, int questionId, int fatherId, int optionId, int score);

    List<Survey> discapacitySurveyList();

    List<SurveyItem> getSubLevelQuestion(List<SurveyDetail> surveyAnswers, int currentQuestionId);

    List<Survey> surveyList(int diagnosticId);

    void deleteSurvey(int surveyId);

    List<Result> grhPractice(int surveyId);

    List<Result> implementationLevels(int surveyId);

    List<Result> directTrait(int surveyId);

    List<Result> combinedTrait(List<Result> directTrait);

    boolean hasSurvey(int diagnosticId);



    List<Result> organizationalCulture(int surveyId);

    List<SurveyItem> getSurveyItemQuestionList(int diagnosticId);
}
