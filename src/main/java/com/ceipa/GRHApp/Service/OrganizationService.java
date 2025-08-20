package com.ceipa.GRHApp.Service;

import com.ceipa.GRHApp.Model.Organization;

import java.util.List;

public interface OrganizationService {

    void saveOrganization(Organization organization);

    Organization findOrganization(String name);

    List<Organization> getOrganizationList();

    void deleteOrganization(String name);

    void updateOrganization(Organization organization);
}
