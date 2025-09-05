package com.ceipa.GRHApp.Mapper;

import com.ceipa.GRHApp.DatabaseEntity.SurveyItemEntity;
import com.ceipa.GRHApp.Model.SurveyItem;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface SurveyItemMapper {

    // Nuevos
    SurveyItem toModel(SurveyItemEntity entity);
    List<SurveyItem> toModelList(List<SurveyItemEntity> entities);

    // Antiguo que reclaman los services
    default List<SurveyItem> mapSurveyItemEntityListToSurveyItemList(List<SurveyItemEntity> entities) {
        return toModelList(entities);
    }
}
