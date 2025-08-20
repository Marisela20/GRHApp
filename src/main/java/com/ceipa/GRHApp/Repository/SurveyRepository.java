package com.ceipa.GRHApp.Repository;

import com.ceipa.GRHApp.DatabaseEntity.SurveyEntity;
import com.ceipa.GRHApp.DatabaseEntity.SurveyItemEntity;
import com.ceipa.GRHApp.Model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<SurveyEntity, Integer> {

    @Query("SELECT s FROM SurveyEntity s WHERE s.user.username = :username AND s.diagnosticId = :diagnosticId")
    SurveyEntity getSurveyByUserAndDiagnosticId(@Param("username") String username, @Param("diagnosticId") int diagnosticId);


    @Query("SELECT s FROM SurveyEntity s LEFT JOIN s.user u LEFT JOIN u.organization o WHERE o.name = ?1 AND s.diagnosticId = ?2 ORDER BY s.id ASC")
    List<SurveyEntity> getSurveyOrganizationList(String name, int diagnosticId);

    @Query("SELECT s FROM SurveyEntity s WHERE s.user.id = ?1 AND s.diagnosticId = ?2")
    List<SurveyEntity> getSurveyListByUser(String username, int diagnosticId);
    List<SurveyEntity> findByUserIdAndDiagnosticId(Integer userId, Integer diagnosticId);


    @Query("SELECT s FROM SurveyEntity s WHERE s.id = ?1")
    SurveyEntity getSurveyById(int surveyId);

    @Query(value = "SELECT * FROM survey s LEFT JOIN user u ON s.user_id = username LEFT JOIN organization o ON u.organization_id = o.name WHERE s.diagnostic_id = ?1 ORDER BY o.name", nativeQuery = true)
    List<SurveyEntity> getSurveyList(int diagnosticId);

    @Query("SELECT s FROM SurveyEntity s WHERE s.diagnosticId = ?1 AND s.completedStatus = 0 ORDER BY s.id ASC")
    List<SurveyEntity> findIncompleteByDiagnosticId(int diagnosticId);

    @Query("SELECT s FROM SurveyEntity s WHERE s.diagnosticId = ?2 AND s.user.id = ?1 AND s.completedStatus = 0 ORDER BY s.id ASC")
    List<SurveyEntity> findActiveByDiagnosticIdAndUserId(String userId, int diagnosticId);
    List<SurveyEntity> findByDiagnosticIdAndCompletedStatus(int diagnosticId, boolean completedStatus);

    List<SurveyEntity> findByDiagnosticId(int diagnosticId);
}