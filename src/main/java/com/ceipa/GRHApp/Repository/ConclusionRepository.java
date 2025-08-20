package com.ceipa.GRHApp.Repository;

import com.ceipa.GRHApp.DatabaseEntity.ConclusionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConclusionRepository extends JpaRepository<ConclusionEntity, Integer> {

    @Query("Select c  from ConclusionEntity c where c.classification.id in (:classificationIds)")
    List<ConclusionEntity> getConclusionsByClassification(List<Integer> classificationIds);
}
