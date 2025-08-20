package com.ceipa.GRHApp.Mapper;

import com.ceipa.GRHApp.DatabaseEntity.ConclusionEntity;
import com.ceipa.GRHApp.Model.Conclusion;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ConclusionMapper {

    List<Conclusion> mapConclusionEntityListToConclusionModelList(List<ConclusionEntity> conclusionEntityList);

    Conclusion conclusionEntityToConclusion(ConclusionEntity conclusionEntity);
}
