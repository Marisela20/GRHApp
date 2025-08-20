package com.ceipa.GRHApp.Repository;

import com.ceipa.GRHApp.DatabaseEntity.DiscapacitySurveyEntity;
import com.ceipa.GRHApp.DatabaseEntity.UserEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscapacitySurveyRepository extends JpaRepository<DiscapacitySurveyEntity, Integer> {

    @Query("SELECT d FROM DiscapacitySurveyEntity d WHERE d.userAccount.username = :username AND d.diagnosticId = :diagnosticId")
    List<DiscapacitySurveyEntity> findByUserUsernameAndDiagnosticId(@Param("username") String username,
                                                                    @Param("diagnosticId") int diagnosticId);

    DiscapacitySurveyEntity findTopByUserAccountAndDiagnosticIdAndCompletedStatusFalseOrderByCreatedAtDesc(UserEntity userAccount, int diagnosticId);
    Optional<DiscapacitySurveyEntity> findTopByUserAccountAndDiagnosticIdOrderByCreatedAtDesc(UserEntity userAccount, int diagnosticId);

    Optional<DiscapacitySurveyEntity> findTopByDiagnosticIdOrderByCreatedAtAsc(int diagnosticId);

    List<DiscapacitySurveyEntity> findByCompletedStatus(boolean status);

    List<DiscapacitySurveyEntity> findByDiagnosticIdAndCompletedStatus(int diagnosticId, boolean completedStatus);

    List<DiscapacitySurveyEntity> findByUserAccountAndCompletedStatus(UserEntity userAccount, Boolean completedStatus);

    Optional<DiscapacitySurveyEntity> findByUserAccountAndDiagnosticId(UserEntity user, Integer diagnosticId);

    Optional<DiscapacitySurveyEntity> findTopByDiagnosticIdAndUserAccountIsNullOrderByCreatedAtDesc(int diagnosticId);

    Optional<DiscapacitySurveyEntity> findTopByUserAccountOrderByCreatedAtDesc(UserEntity userAccount);

    DiscapacitySurveyEntity findTopByUserAccount_IdAndDiagnosticIdOrderByCreatedAtDesc(Integer userId, int diagnosticId);
}
