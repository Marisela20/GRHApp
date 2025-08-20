package com.ceipa.GRHApp.Repository;

import com.ceipa.GRHApp.DatabaseEntity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscapacityResponseRepository extends JpaRepository<DiscapacityResponseEntity, Integer> {

    // ✅ Todas las respuestas de un survey
    List<DiscapacityResponseEntity> findBySurveyId(int surveyId);
    List<DiscapacityResponseEntity> findBySurvey_Id(int surveyId);

    // ✅ Eliminar todas las respuestas de un survey (JPQL)
    @Modifying
    @Query("delete from DiscapacityResponseEntity r where r.survey.id = :surveyId")
    void deleteBySurvey_Id(@Param("surveyId") int surveyId);

    // ✅ Última respuesta por pregunta
    Optional<DiscapacityResponseEntity> findTopBySurveyIdAndQuestionIdOrderByCreatedAtDesc(int surveyId, int questionId);

    // Histórica (si la usas)
    Optional<DiscapacityResponseEntity> findBySurveyIdAndQuestionId(int surveyId, int questionId);

    // ✅ Todas las respuestas por pregunta y encuesta
    List<DiscapacityResponseEntity> findAllBySurveyIdAndQuestionId(int surveyId, int questionId);

    // ✅ Eliminar por usuario y pregunta
    void deleteByUserAccountAndQuestion(UserEntity userAccount, DiscapacityQuestionEntity question);

    // ✅ Existe respuesta para una pregunta del survey
    boolean existsBySurvey_IdAndQuestion_Id(int surveyId, int questionId);

    // ✅ Por entidad survey
    List<DiscapacityResponseEntity> findBySurvey(DiscapacitySurveyEntity survey);

    // ✅ Específica
    Optional<DiscapacityResponseEntity> findBySurveyAndQuestion(DiscapacitySurveyEntity survey,
                                                                DiscapacityQuestionEntity question);

    // ============= NUEVOS MÉTODOS PARA BORRADORES POR SECCIÓN =============

    // Buscar borrador de una pregunta para (survey, question, user, isDraft)
    List<DiscapacityResponseEntity>
    findAllBySurvey_IdAndQuestion_IdAndUserAccount_IdAndIsDraft(
            Integer surveyId, Integer questionId, Integer userId, Boolean isDraft
    );

    // Borrar borradores de una sección (si lo necesitas)
    void deleteBySurvey_IdAndSectionAndUserAccount_IdAndIsDraft(
            Integer surveyId, Integer section, Integer userId, Boolean isDraft
    );

    // Publicar todos los borradores del usuario (set isDraft=false)
    @Modifying
    @Query("UPDATE DiscapacityResponseEntity r SET r.isDraft = false " +
            "WHERE r.survey.id = :surveyId AND r.userAccount.id = :userId")
    int publishDrafts(@Param("surveyId") Integer surveyId, @Param("userId") Integer userId);
}
