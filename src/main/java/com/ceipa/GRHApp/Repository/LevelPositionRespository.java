package com.ceipa.GRHApp.Repository;

import com.ceipa.GRHApp.DatabaseEntity.LevelPositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelPositionRespository extends JpaRepository<LevelPositionEntity, Integer> {
}
