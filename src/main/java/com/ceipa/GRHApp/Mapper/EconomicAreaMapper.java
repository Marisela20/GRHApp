package com.ceipa.GRHApp.Mapper;

import com.ceipa.GRHApp.DatabaseEntity.EconomicAreaEntity;
import com.ceipa.GRHApp.Model.EconomicArea;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface EconomicAreaMapper {

    List<EconomicArea> mapEconomicAreaEntityListToEconomicAreaModelList(List<EconomicAreaEntity> economicAreaEntityList);
}
