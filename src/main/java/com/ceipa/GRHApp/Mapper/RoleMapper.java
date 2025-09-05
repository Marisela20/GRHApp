package com.ceipa.GRHApp.Mapper;

import com.ceipa.GRHApp.DatabaseEntity.RoleEntity;
import com.ceipa.GRHApp.Model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface RoleMapper {

    // mapeo 1→1 recomendado
    Role toModel(RoleEntity entity);

    // mapeo de listas recomendado
    List<Role> toModelList(List<RoleEntity> entities);

    // alias para no tocar los services existentes
    default List<Role> mapRoleEntityListToRoleList(List<RoleEntity> roleEntityList) {
        return toModelList(roleEntityList);
    }
}
