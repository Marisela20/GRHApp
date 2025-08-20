package com.ceipa.GRHApp.Repository;

import com.ceipa.GRHApp.DatabaseEntity.ClassificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificationRepository extends JpaRepository<ClassificationEntity, Integer> {

}
