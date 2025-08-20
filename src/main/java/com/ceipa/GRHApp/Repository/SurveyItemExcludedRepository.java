package com.ceipa.GRHApp.Repository;

import com.ceipa.GRHApp.DatabaseEntity.SurveyItemExcludedEntity;
import com.ceipa.GRHApp.DatabaseEntity.SurveyItemExcludedKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyItemExcludedRepository extends JpaRepository<SurveyItemExcludedEntity, SurveyItemExcludedKey> {

    @Query("select s from SurveyItemExcludedEntity s")
    List<SurveyItemExcludedEntity> findAllSubLevelExcluded();

    @Query("select s from SurveyItemExcludedEntity s where s.question.id = ?1")
    List<SurveyItemExcludedEntity> findSurveyItemExcludedByQuestionId(int questionId);
}
