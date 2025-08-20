package com.ceipa.GRHApp.Mapper;

import com.ceipa.GRHApp.DatabaseEntity.RoleEntity;
import com.ceipa.GRHApp.Model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RoleMapper {


    List<Role> mapRoleEntityListToRoleList(List<RoleEntity> roleEntityList);

}
