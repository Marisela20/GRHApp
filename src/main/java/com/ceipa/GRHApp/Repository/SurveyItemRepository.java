package com.ceipa.GRHApp.Repository;

import com.ceipa.GRHApp.DatabaseEntity.SurveyItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyItemRepository extends JpaRepository<SurveyItemEntity, Integer> {

    @Query("SELECT s FROM SurveyItemEntity s WHERE s.classification.id = ?1 ORDER BY s.id ASC")
    List<SurveyItemEntity> getSurveyItemQuestionListByClassificationId(int classificationId);

    @Query("SELECT s FROM SurveyItemEntity s WHERE s.classification.id = ?1 AND s.isQuestion = 0 ORDER BY s.id ASC")
    List<SurveyItemEntity> getSurveyItemAnswerListByClassificationId(int classificationId);

    @Query("SELECT s FROM SurveyItemEntity s WHERE s.id = ?1 OR s.father.id = ?1 ORDER BY s.id ASC")
    List<SurveyItemEntity> getSurveyItemQuestionListById(int surveyItemId);

    @Query("SELECT s FROM SurveyItemEntity s WHERE s.classification.id = ?1 AND s.isQuestion = 1 AND isSublevel = 0 ORDER BY s.id ASC")
    List<SurveyItemEntity> getSurveyItemQuestionListByClassification(int classificationId);

    @Query("SELECT s FROM SurveyItemEntity s WHERE s.isQuestion = 1 AND isSublevel = 0 AND s.diagnosticId = ?1 ORDER BY s.id ASC")
    List<SurveyItemEntity> getSurveyItemQuestionList(int diagnosticId);

    @Query("SELECT s FROM SurveyItemEntity s WHERE s.isQuestion = 0 AND isSublevel = 0 AND section = 2 ORDER BY s.id ASC")
    List<SurveyItemEntity> getSurveyItemResponseSectionTwo();

    @Query("SELECT s FROM SurveyItemEntity s WHERE s.classification.id = ?1 AND s.father.id = ?2 ORDER BY s.id ASC")
    List<SurveyItemEntity> getSurveyItemQuestionListByClassificationIdAndFatherId(int classificationId, int fatherId);

    @Query("SELECT s FROM SurveyItemEntity s LEFT JOIN FETCH s.surveyAnswerOptions WHERE s.diagnosticId = :diagnosticId AND s.section = :section ORDER BY s.order ASC")
    List<SurveyItemEntity> findByDiagnosticIdAndSection(@Param("diagnosticId") int diagnosticId,
                                                        @Param("section") int section);

    @Query("SELECT s FROM SurveyItemEntity s WHERE s.diagnosticId = :diagnosticId ORDER BY s.order ASC")
    List<SurveyItemEntity> findByDiagnosticId(@Param("diagnosticId") int diagnosticId);
}
