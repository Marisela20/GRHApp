package com.ceipa.GRHApp.Mapper;

import com.ceipa.GRHApp.DatabaseEntity.ClassificationEntity;
import com.ceipa.GRHApp.DatabaseEntity.SurveyDetailEntity;
import com.ceipa.GRHApp.Model.Survey;
import com.ceipa.GRHApp.Model.SurveyDetail;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface SurveyDetailMapper {

    // ✅ Método para convertir modelo + detalle en entidad
    @Mappings({
            @Mapping(source = "survey.id", target = "survey.id"),
            @Mapping(source = "detail.question.id", target = "question.id"),
            @Mapping(source = "detail.question.classification", target = "question.classification"),
            @Mapping(source = "detail.answer.id", target = "answer.id"),
            @Mapping(source = "detail.subQuestion.id", target = "subQuestion.id"),
            @Mapping(source = "detail.subAnswer.id", target = "subAnswer.id")
    })
    SurveyDetailEntity mapSurveyDetailModelToSurveyDetailEntity(Survey survey, SurveyDetail detail);

    // ✅ Detalle solo → Entidad
    @Mappings({
            @Mapping(source = "question.id", target = "question.id"),
            @Mapping(source = "question.classification", target = "question.classification")
    })
    SurveyDetailEntity mapSurveyDetailToSurveyDetailEntity(SurveyDetail detail);

    // ✅ Entidad → Detalle (modelo)
    @Mappings({
            @Mapping(source = "question.id", target = "question.id"),
            @Mapping(source = "question.classification", target = "question.classification")
    })
    SurveyDetail mapSurveyDetailEntityToSurveyDetail(SurveyDetailEntity detail);

    List<SurveyDetail> mapSurveyDetailEntityListToSurveyDetail(List<SurveyDetailEntity> list);

    // 🔁 Conversión auxiliar para Entidad → String (usando description)
    default String map(ClassificationEntity entity) {
        return entity != null ? entity.getDescription() : null;
    }

    // 🔁 Conversión auxiliar para String → Entidad (usando description)
    default ClassificationEntity map(String classification) {
        if (classification == null) return null;
        ClassificationEntity entity = new ClassificationEntity();
        entity.setDescription(classification);
        return entity;
    }
}
