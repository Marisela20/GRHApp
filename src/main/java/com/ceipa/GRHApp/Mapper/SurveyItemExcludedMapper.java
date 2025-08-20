package com.ceipa.GRHApp.Mapper;

import com.ceipa.GRHApp.DatabaseEntity.SurveyItemExcludedEntity;
import com.ceipa.GRHApp.Model.SurveyItemExcluded;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface SurveyItemExcludedMapper {

    List<SurveyItemExcluded> mapSurveyItemExcludedEntityListToSurveyItemExcludedModelList(List<SurveyItemExcludedEntity> surveyItemExcludedEntityList);
}
