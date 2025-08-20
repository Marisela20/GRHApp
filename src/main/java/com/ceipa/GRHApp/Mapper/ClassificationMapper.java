package com.ceipa.GRHApp.Mapper;

import com.ceipa.GRHApp.DatabaseEntity.ClassificationEntity;
import com.ceipa.GRHApp.Model.Classification;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface ClassificationMapper {

    Classification mapClassificationEntityToClassification(ClassificationEntity classificationEntity);
}
