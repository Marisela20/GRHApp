package com.ceipa.GRHApp.Mapper;

import com.ceipa.GRHApp.DatabaseEntity.SurveyItemEntity;
import com.ceipa.GRHApp.Model.SurveyItem;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {ClassificationMapper.class},
        unmappedTargetPolicy = ReportingPolicy.WARN,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface SurveyItemMapper {

    List<SurveyItem> mapSurveyItemEntityListToSurveyItemList(List<SurveyItemEntity> surveyItemEntityList);

    @Mappings({
            @Mapping(target = "question", source = "isQuestion"),
            @Mapping(target = "sublevel", source = "isSublevel"),
            @Mapping(target = "classification", source = "classification")
    })
    SurveyItem surveyItemEntityToSurveyItem(SurveyItemEntity surveyItemEntity);
}
