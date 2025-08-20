package com.ceipa.GRHApp.Mapper;

import com.ceipa.GRHApp.DatabaseEntity.ClassificationEntity;
import com.ceipa.GRHApp.DatabaseEntity.SurveyEntity;
import com.ceipa.GRHApp.Model.Survey;
import com.ceipa.GRHApp.Model.UserAccount;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {SurveyDetailMapper.class}
)
public interface SurveyMapper {

    // ✅ Lista completa de entidades a modelos
    List<Survey> mapSurveyEntityListToSurvey(List<SurveyEntity> SurveyEntityList);

    // ✅ Mapeo de entidad a modelo principal
    @Mapping(target = "id", source = "surveyEntity.id")  // 👈 especificamos el ID directamente
    @Mapping(target = "surveyDetailList", source = "surveyEntity.surveyDetailEntityList")
    @Mapping(target = "userAccount", source = "surveyEntity.user")
    @Mapping(target = "completedStatus", source = "surveyEntity.completedStatus")
    @Mapping(target = "date", source = "surveyEntity.date")
    @Mapping(target = "diagnosticId", source = "surveyEntity.diagnosticId")
    Survey mapSurveyEntityToSurvey(SurveyEntity surveyEntity);

    // ✅ Mapeo inverso de modelo a entidad
    @Mapping(target = "id", source = "survey.id") // 👈 especificamos el ID
    @Mapping(target = "user", source = "survey.userAccount")
    @Mapping(target = "surveyDetailEntityList", source = "survey.surveyDetailList")
    @Mapping(target = "completedStatus", source = "survey.completedStatus")
    @Mapping(target = "date", source = "survey.date")
    @Mapping(target = "diagnosticId", source = "survey.diagnosticId")
    SurveyEntity mapSurveyToSurveyEntity(Survey survey);

    // ✅ Si necesitas un método que asigna manualmente campos adicionales
    @Mapping(target = "id", source = "surveyEntity.id")
    @Mapping(target = "surveyDetailList", source = "surveyEntity.surveyDetailEntityList")
    @Mapping(target = "diagnosticId", source = "diagnosticId")
    @Mapping(target = "completedStatus", source = "surveyEntity.completedStatus")
    @Mapping(target = "date", source = "surveyEntity.date")
    @Mapping(target = "userAccount", source = "userAccount")
    Survey mapSurveyEntityToSurveyModel(SurveyEntity surveyEntity, UserAccount userAccount, int diagnosticId);

    // ✅ Métodos auxiliares para clasificación (si usas clasificación como String)
    default String map(ClassificationEntity entity) {
        return entity != null ? entity.getDescription() : null;
    }

    default ClassificationEntity map(String classification) {
        if (classification == null) return null;
        ClassificationEntity entity = new ClassificationEntity();
        entity.setDescription(classification);
        return entity;
    }
}
