package com.ceipa.GRHApp.Repository;

import com.ceipa.GRHApp.DatabaseEntity.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrganizationRespository extends JpaRepository<OrganizationEntity, String> {
    OrganizationEntity findByName(String name);


}
