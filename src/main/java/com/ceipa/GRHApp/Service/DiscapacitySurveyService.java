package com.ceipa.GRHApp.Service;

import com.ceipa.GRHApp.DatabaseEntity.DiscapacityResponseEntity;
import com.ceipa.GRHApp.DatabaseEntity.DiscapacitySurveyEntity;
import com.ceipa.GRHApp.DatabaseEntity.UserEntity;
import com.ceipa.GRHApp.Model.*;

import java.util.List;
import java.util.Map;

public interface DiscapacitySurveyService {

    List<DiscapacityQuestion> getQuestionsBySurveyAndSection(int surveyId, int section);

    void saveResponses(int surveyId, Map<String, String> responses);

    void deleteBySurveyId(int surveyId);

    void markSurveyAsCompleted(int surveyId);

    void saveSurveyResponses(int surveyId, List<DiscapacityResponse> responses);

    List<DiscapacityQuestion> getQuestions(int surveyId);

    List<DiscapacityQuestion> getQuestionsBySurveyId(int surveyId);

    boolean hasUserCompletedSurvey(int surveyId);

    List<DiscapacityQuestion> getQuestionsByOrderRange(int startOrder, int endOrder);

    Survey findOrCreateSurveyForUser(UserAccount user);

    DiscapacitySurveyEntity getLastSurveyForCurrentUserOrCreate(String username);

    Survey getSurveyById(int surveyId);

    Survey getLastSurveyForCurrentUser(int diagnosticId);

    Survey convertToSurveyModel(DiscapacitySurveyEntity entity);

    List<Survey> discapacitySurveyList();

    Map<Integer, List<Integer>> getUserResponsesMap(int surveyId);

    int createNewDiscapacitySurveyForCurrentUser();

    List<DiscapacityQuestion> getSurveyQuestionsBySurveyId(int surveyId);

    List<DiscapacityQuestion> getSurveyQuestionsBySurveyIdAndSection(int surveyId, int section);

    List<DiscapacityQuestion> getDiscapacitySurveyItem(Survey survey, int section);

    List<DiscapacityQuestion> getDiscapacitySurveyItemByUser(UserEntity user, int section);

    void saveDiscapacitySurveyDetail(Map<String, String[]> parameterMap, int surveyId);

    void saveDiscapacityManualAnswer(int surveyId, int questionId, String freeText);

    List<DiscapacityResponseEntity> getResponsesBySurveyId(int surveyId);

    List<DiscapacityResponse> convertEntitiesToModels(List<DiscapacityResponseEntity> entityList);
    // DiscapacitySurveyService.java
    void ensurePopulatedSurvey(int destSurveyId);
    void applySectionMapping(int surveyId);

    // ✅ MÉTODO NECESARIO PARA EL CONTROLADOR
    Map<Integer, List<Integer>> getResponsesGroupedByQuestion(int surveyId);
}
