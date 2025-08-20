package com.ceipa.GRHApp.Service;

import com.ceipa.GRHApp.Mapper.EconomicAreaMapper;
import com.ceipa.GRHApp.Model.EconomicArea;
import com.ceipa.GRHApp.Repository.EconomicAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EconomicAreaServiceImpl implements EconomicAreaService {

    @Autowired
    private EconomicAreaRepository economicAreaRepository;

    @Autowired
    private EconomicAreaMapper economicAreaMapper;

    @Override
    public List<EconomicArea> getEconomicAreaList() {
        return economicAreaMapper.mapEconomicAreaEntityListToEconomicAreaModelList(economicAreaRepository.findAll());
    }
}
