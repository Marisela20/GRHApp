package com.ceipa.GRHApp.Repository;

import com.ceipa.GRHApp.DatabaseEntity.DiscapacityQuestionEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscapacityQuestionRepository extends JpaRepository<DiscapacityQuestionEntity, Integer> {

    // 🔹 Todas las preguntas de una encuesta
    List<DiscapacityQuestionEntity> findBySurvey_Id(int surveyId);

    // 🔹 Por encuesta y sección, ordenadas
    List<DiscapacityQuestionEntity> findBySurvey_IdAndSectionOrderByOrderNumAsc(int surveyId, int section);

    // 🔹 Fallback: sección NULL (plantillas antiguas)
    List<DiscapacityQuestionEntity> findBySurvey_IdAndSectionIsNullOrderByOrderNumAsc(int surveyId);

    // 🔹 TODAS por encuesta, orden global por sección y orden
    List<DiscapacityQuestionEntity> findBySurvey_IdOrderBySectionAscOrderNumAsc(int surveyId);

    // 🔹 Por diagnóstico
    List<DiscapacityQuestionEntity> findBySurvey_DiagnosticId(int diagnosticId);

    // 🔹 Por diagnóstico y sección (ordenadas)
    List<DiscapacityQuestionEntity> findBySurvey_DiagnosticIdAndSectionOrderByOrderNumAsc(int diagnosticId, int section);

    // 🔹 Por diagnóstico orden global
    List<DiscapacityQuestionEntity> findBySurvey_DiagnosticIdOrderBySectionAscOrderNumAsc(int diagnosticId);

    // 🔹 Plantilla base (sin usuario)
    @Query("SELECT q FROM DiscapacityQuestionEntity q " +
            "WHERE q.survey.diagnosticId = :diagnosticId AND q.survey.userAccount IS NULL")
    List<DiscapacityQuestionEntity> findBaseQuestionsByDiagnosticId(@Param("diagnosticId") int diagnosticId);

    // 🔹 Con opciones (JOIN FETCH)
    @Query("SELECT DISTINCT q FROM DiscapacityQuestionEntity q " +
            "LEFT JOIN FETCH q.options " +
            "WHERE q.survey.id = :surveyId AND q.section = :section " +
            "ORDER BY q.orderNum")
    List<DiscapacityQuestionEntity> findBySurveyIdAndSectionWithOptions(@Param("surveyId") int surveyId,
                                                                        @Param("section") int section);

    // 🔹 Verificar existencia por encuesta y orden
    boolean existsBySurvey_IdAndOrderNum(int surveyId, int orderNum);

    // 🔹 Rango por orden
    @Query("SELECT q FROM DiscapacityQuestionEntity q " +
            "WHERE q.survey.id = :surveyId AND q.orderNum BETWEEN :startOrder AND :endOrder " +
            "ORDER BY q.orderNum")
    List<DiscapacityQuestionEntity> findBySurveyIdAndOrderRange(@Param("surveyId") int surveyId,
                                                                @Param("startOrder") int startOrder,
                                                                @Param("endOrder") int endOrder);

    // 🔹 IDs de preguntas por survey (para borrar opciones)
    @Query("select q.id from DiscapacityQuestionEntity q where q.survey.id = :surveyId")
    List<Integer> findIdsBySurveyId(@Param("surveyId") int surveyId);

    // 🔹 Borrado masivo de preguntas por survey
    @Modifying
    @Query("delete from DiscapacityQuestionEntity q where q.survey.id = :surveyId")
    void deleteBySurvey_Id(@Param("surveyId") int surveyId);
    // 🔹 Máximo número de sección por encuesta
    @Query("SELECT COALESCE(MAX(q.section), 1) FROM DiscapacityQuestionEntity q WHERE q.survey.id = :surveyId")
    int findMaxSectionBySurveyId(@Param("surveyId") int surveyId);

    // 🔹 Total de preguntas (para el header)
    long countBySurvey_Id(int surveyId);
}
