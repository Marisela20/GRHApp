package com.ceipa.GRHApp.Service;

import com.ceipa.GRHApp.Model.Role;

import java.util.List;

public interface RoleService {
    void saveRole(Role role);
    Role findRole(int id);
    Role findByName(String name);
    List<Role> getRoleList();
}
