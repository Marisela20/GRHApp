package com.ceipa.GRHApp.Mapper;

import com.ceipa.GRHApp.DatabaseEntity.SurveyItemExcludedEntity;
import com.ceipa.GRHApp.Model.SurveyItemExcluded;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = { SurveyItemMapper.class }, // si la entidad tiene campos SurveyItemEntity -> SurveyItem
        unmappedTargetPolicy = ReportingPolicy.WARN,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface SurveyItemExcludedMapper {

    // nombres “canónicos” de MapStruct
    SurveyItemExcluded toModel(SurveyItemExcludedEntity entity);
    List<SurveyItemExcluded> toModelList(List<SurveyItemExcludedEntity> entities);

    // alias con el nombre que esperan tus services (no cambies los services)
    default List<SurveyItemExcluded> mapSurveyItemExcludedEntityListToSurveyItemExcludedModelList(
            List<SurveyItemExcludedEntity> entities) {
        return toModelList(entities);
    }
}
