package com.ceipa.GRHApp.Repository;

import com.ceipa.GRHApp.DatabaseEntity.LevelPositionEntity;
import com.ceipa.GRHApp.DatabaseEntity.OrganizationEntity;
import com.ceipa.GRHApp.DatabaseEntity.RoleEntity;
import com.ceipa.GRHApp.DatabaseEntity.UserEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    // ✅ búsquedas case-insensitive
    Optional<UserEntity> findByUsernameIgnoreCase(String username);
    Optional<UserEntity> findByEmailIgnoreCase(String email);

    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByEmailIgnoreCase(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.organization.name = :orgName")
    List<UserEntity> findByOrganization(@Param("orgName") String orgName);

    @Query("SELECT u FROM UserEntity u WHERE u.organization IS NOT NULL")
    List<UserEntity> findAllWithOrganization();

    @Modifying
    @Query("UPDATE UserEntity u SET u.acceptPolicy = true WHERE LOWER(u.username) = LOWER(:username)")
    void updateUserPolicy(@Param("username") String username);

    // Helpers sin repositorios separados
    @Query("SELECT r FROM RoleEntity r WHERE r.name = :name")
    RoleEntity findRoleEntityByName(@Param("name") String name);

    @Query("SELECT o FROM OrganizationEntity o WHERE o.name = :name")
    OrganizationEntity findOrganizationEntityByName(@Param("name") String name);

    @Query("SELECT l FROM LevelPositionEntity l WHERE l.id = :id")
    LevelPositionEntity findLevelPositionEntityById(@Param("id") int id);
}
