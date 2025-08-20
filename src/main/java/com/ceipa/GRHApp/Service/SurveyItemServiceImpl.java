package com.ceipa.GRHApp.Service;

import com.ceipa.GRHApp.Mapper.SurveyItemMapper;
import com.ceipa.GRHApp.Model.SurveyItem;
import com.ceipa.GRHApp.Repository.SurveyItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyItemServiceImpl implements SurveyItemservice{

    @Autowired
    private SurveyItemRepository surveyItemRepository;

    @Autowired
    private SurveyItemMapper surveyItemMapper;

    @Override
    public List<SurveyItem> getSurveyItemListByClassification(int classification) {
        return surveyItemMapper.mapSurveyItemEntityListToSurveyItemList(surveyItemRepository.getSurveyItemQuestionListByClassificationId(classification));
    }

    @Override
    public List<SurveyItem> getSurveyItemListById(int surveyItemId) {
        return surveyItemMapper.mapSurveyItemEntityListToSurveyItemList(surveyItemRepository.getSurveyItemQuestionListById(surveyItemId));
    }

    @Override
    public List<SurveyItem> getSurveyItemQuestionListByClassification(int classification) {
        return surveyItemMapper.mapSurveyItemEntityListToSurveyItemList(surveyItemRepository.getSurveyItemQuestionListByClassification(classification));
    }

    @Override
    public List<SurveyItem> getSurveyItemQuestionList(int diagnosticId) {
        System.out.println(surveyItemRepository.getSurveyItemQuestionList(diagnosticId));
        return surveyItemMapper.mapSurveyItemEntityListToSurveyItemList(surveyItemRepository.getSurveyItemQuestionList(diagnosticId));
    }

    @Override
    public List<SurveyItem> getSurveyItemResponseSectionTwo() {
        return surveyItemMapper.mapSurveyItemEntityListToSurveyItemList(surveyItemRepository.getSurveyItemResponseSectionTwo());
    }

    @Override
    public List<SurveyItem> getSurveyItemListByClassificationAndFatherId(int classification, int fatherId) {
        return surveyItemMapper.mapSurveyItemEntityListToSurveyItemList(surveyItemRepository.getSurveyItemQuestionListByClassificationIdAndFatherId(classification, fatherId));
    }

    @Override
    public List<SurveyItem> getSurveyItemAnswerListByClassificationId(int classification) {
        return surveyItemMapper.mapSurveyItemEntityListToSurveyItemList(surveyItemRepository.getSurveyItemAnswerListByClassificationId(classification));
    }
}
