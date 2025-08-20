package com.ceipa.GRHApp.Service;

import com.ceipa.GRHApp.DatabaseEntity.RoleEntity;
import com.ceipa.GRHApp.Mapper.RoleMapper;
import com.ceipa.GRHApp.Model.Role;
import com.ceipa.GRHApp.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void saveRole(Role role) {
        RoleEntity entity = new RoleEntity();
        entity.setId(role.getId());
        entity.setName(role.getName());
        roleRepository.save(entity);
    }

    @Override
    public Role findByName(String name) {
        RoleEntity entity = roleRepository.findByName(name);
        return convertToModel(entity);
    }

    @Override
    public Role findRole(int id) {
        RoleEntity entity = roleRepository.findById(id).orElse(null);
        return convertToModel(entity);
    }

    @Override
    public List<Role> getRoleList() {
        return roleMapper.mapRoleEntityListToRoleList(roleRepository.findAll());
    }

    private Role convertToModel(RoleEntity entity) {
        if (entity == null) return null;
        Role role = new Role();
        role.setId(entity.getId());
        role.setName(entity.getName());
        return role;
    }
}
