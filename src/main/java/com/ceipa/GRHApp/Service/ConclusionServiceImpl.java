package com.ceipa.GRHApp.Service;

import com.ceipa.GRHApp.Mapper.ConclusionMapper;
import com.ceipa.GRHApp.Model.Conclusion;
import com.ceipa.GRHApp.Repository.ConclusionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConclusionServiceImpl implements ConclusionService{

    @Autowired
    private ConclusionRepository conclusionRepository;

    @Autowired
    private ConclusionMapper conclusionMapper;

    @Override
    public List<Conclusion> getConlusionListByClassificationIds(List<Integer> classificationIds) {

        return  conclusionMapper.mapConclusionEntityListToConclusionModelList(conclusionRepository.getConclusionsByClassification(classificationIds));
    }
}
