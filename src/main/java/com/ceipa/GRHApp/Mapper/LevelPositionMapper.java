package com.ceipa.GRHApp.Mapper;

import com.ceipa.GRHApp.DatabaseEntity.LevelPositionEntity;
import com.ceipa.GRHApp.Model.LevelPosition;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface LevelPositionMapper {

    List<LevelPosition> mapLevelPositionEntityListToLevelPositionList(List<LevelPositionEntity> levelPositionEntityList);
}
