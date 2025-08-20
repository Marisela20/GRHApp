package com.ceipa.GRHApp.Repository;

import com.ceipa.GRHApp.DatabaseEntity.SurveyDetailEntity;
import com.ceipa.GRHApp.DatabaseEntity.SurveyDetailEntityKey;
import com.ceipa.GRHApp.DatabaseEntity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SurveyDetailRepository extends JpaRepository<SurveyDetailEntity, SurveyDetailEntityKey> {

    @Transactional
    @Modifying
    @Query("DELETE FROM SurveyDetailEntity sde WHERE sde.survey.id = ?1")
    void deleteSurveyDetailBySurveyId(int surveyId);

    @Query("SELECT SUM(sa.score) FROM SurveyDetailEntity s LEFT JOIN s.subAnswer sa WHERE s.question.classification.id = ?1 AND s.survey.id = ?2")
    Integer getAmount(int classification, int surveyId);

    @Query("SELECT SUM(sa.score) FROM SurveyDetailEntity s LEFT JOIN s.subAnswer sa WHERE s.subQuestion.classification.id = ?1 AND s.survey.id = ?2")
    Integer getSubLevelAmount(int classification, int surveyId);

    @Query("SELECT SUM(sa.score) FROM SurveyDetailEntity s LEFT JOIN s.answer sa WHERE s.question.classification.id = ?1 AND s.survey.id = ?2")
    Integer getSecondSectionAmount(int classification, int surveyId);

    @Query("SELECT s FROM SurveyDetailEntity s LEFT JOIN s.answer sa WHERE s.question.classification.id = ?1 AND s.survey.id = ?2")
    List<SurveyDetailEntity> getByClassificationAndSurveyId(int classification, int surveyId);
}
