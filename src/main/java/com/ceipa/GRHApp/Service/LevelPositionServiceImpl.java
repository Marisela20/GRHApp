package com.ceipa.GRHApp.Service;

import com.ceipa.GRHApp.Mapper.LevelPositionMapper;
import com.ceipa.GRHApp.Model.LevelPosition;
import com.ceipa.GRHApp.Repository.LevelPositionRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelPositionServiceImpl implements LevelPositionService{

    @Autowired
    private LevelPositionMapper levelPositionMapper;

    @Autowired
    private LevelPositionRespository levelPositionRespository;

    @Override
    public List<LevelPosition> getAll() {
        return levelPositionMapper.mapLevelPositionEntityListToLevelPositionList(levelPositionRespository.findAll());
    }
}
