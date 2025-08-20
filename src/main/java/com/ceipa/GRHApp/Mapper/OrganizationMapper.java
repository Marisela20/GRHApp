package com.ceipa.GRHApp.Mapper;

import com.ceipa.GRHApp.DatabaseEntity.OrganizationEntity;
import com.ceipa.GRHApp.Model.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OrganizationMapper {

    List<Organization> organizationEntityListToOrganizationList(List<OrganizationEntity> organizationEntity);

    OrganizationEntity organizationModelToOrganizationEntity(Organization organization);

    Organization organizationEntityToOrganization(OrganizationEntity organizationEntity);

}
