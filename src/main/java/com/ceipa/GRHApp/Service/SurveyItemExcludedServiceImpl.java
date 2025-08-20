package com.ceipa.GRHApp.Service;

import com.ceipa.GRHApp.Mapper.SurveyItemExcludedMapper;
import com.ceipa.GRHApp.Model.SurveyItemExcluded;
import com.ceipa.GRHApp.Repository.SurveyItemExcludedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyItemExcludedServiceImpl implements SurveyItemExcludedService{

    @Autowired
    private SurveyItemExcludedMapper surveyItemExcludedMapper;

    @Autowired
    private SurveyItemExcludedRepository surveyItemExcludedRepository;

    @Override
    public List<SurveyItemExcluded> getSurveyItemExcludedList() {
        return surveyItemExcludedMapper.mapSurveyItemExcludedEntityListToSurveyItemExcludedModelList(surveyItemExcludedRepository.findAllSubLevelExcluded());
    }

    @Override
    public List<SurveyItemExcluded> getSurveyItemExcludedListByQuestionId(int questionId) {
        return surveyItemExcludedMapper.mapSurveyItemExcludedEntityListToSurveyItemExcludedModelList(surveyItemExcludedRepository.findSurveyItemExcludedByQuestionId(questionId));
    }
}
