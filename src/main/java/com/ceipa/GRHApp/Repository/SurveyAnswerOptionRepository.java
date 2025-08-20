package com.ceipa.GRHApp.Repository;

import com.ceipa.GRHApp.DatabaseEntity.SurveyAnswerOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyAnswerOptionRepository extends JpaRepository<SurveyAnswerOptionEntity, Integer> {

    List<SurveyAnswerOptionEntity> findBySurveyItemId(int surveyItemId);
}
