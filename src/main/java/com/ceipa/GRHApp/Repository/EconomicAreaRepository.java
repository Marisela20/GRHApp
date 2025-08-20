package com.ceipa.GRHApp.Repository;

import com.ceipa.GRHApp.DatabaseEntity.EconomicAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EconomicAreaRepository extends JpaRepository <EconomicAreaEntity, Integer> {
}
