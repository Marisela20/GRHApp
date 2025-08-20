package com.ceipa.GRHApp.Mapper;

import com.ceipa.GRHApp.DatabaseEntity.DiscapacitySurveyEntity;
import com.ceipa.GRHApp.DatabaseEntity.DiscapacityQuestionEntity;
import com.ceipa.GRHApp.DatabaseEntity.DiscapacityAnswerOptionEntity;
import com.ceipa.GRHApp.DatabaseEntity.DiscapacityResponseEntity;
import com.ceipa.GRHApp.Model.*;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DiscapacitySurveyMapper {

    // 🔁 Conversión de entidad a modelo (encuesta)
    Survey toModel(DiscapacitySurveyEntity entity);

    // 🔁 Conversión de modelo a entidad (encuesta)
    DiscapacitySurveyEntity toEntity(Survey model);

    // 🔁 Conversión de entidad a modelo (pregunta)
    DiscapacityQuestion entityToModel(DiscapacityQuestionEntity entity);

    // 🔁 Opcionales, pero recomendados
    DiscapacityAnswerOption optionEntityToModel(DiscapacityAnswerOptionEntity entity);

    DiscapacityResponse responseEntityToModel(DiscapacityResponseEntity entity);
}
