package com.ceipa.GRHApp.Service;

import com.ceipa.GRHApp.Mapper.SurveyDetailMapper;
import com.ceipa.GRHApp.Model.SurveyDetail;
import com.ceipa.GRHApp.Repository.SurveyDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyDetailServiceImpl implements SurveyDetailService{

    @Autowired
    private SurveyDetailMapper surveyDetailMapper;

    @Autowired
    private SurveyDetailRepository surveyDetailRepository;

    @Override
    public List<SurveyDetail> getSurveyDetailList() {
        return surveyDetailMapper.mapSurveyDetailEntityListToSurveyDetail(surveyDetailRepository.findAll());
    }
}
