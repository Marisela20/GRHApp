package com.ceipa.GRHApp.Service;

import com.ceipa.GRHApp.DatabaseEntity.OrganizationEntity;
import com.ceipa.GRHApp.DatabaseEntity.RoleEntity;
import com.ceipa.GRHApp.DatabaseEntity.UserEntity;
import com.ceipa.GRHApp.Mapper.OrganizationMapper;
import com.ceipa.GRHApp.Model.Organization;
import com.ceipa.GRHApp.Repository.OrganizationRespository;
import com.ceipa.GRHApp.Repository.RoleRepository;
import com.ceipa.GRHApp.Repository.UserRepository;
import com.ceipa.GRHApp.Util.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRespository organizationRespository;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // ✅ Corrección aquí

    @Override
    public void saveOrganization(Organization organization) {
        try {
            // 0) Validaciones básicas
            if (organization == null) {
                throw new RepositoryException("Organización inválida");
            }
            if (organization.getName() == null || organization.getName().trim().isEmpty()) {
                throw new RepositoryException("El nombre de la organización es obligatorio");
            }

            isOrganizationNameValid(organization.getName()); // tu validación existente

            // 1) Guardar la organización
            OrganizationEntity entity = organizationMapper.organizationModelToOrganizationEntity(organization);
            organizationRespository.save(entity);

            // 2) Crear automáticamente el usuario responsable (si hay datos)
            String rawEmail = organization.getEmail();
            String workerName = organization.getWorker();
            if (rawEmail != null && !rawEmail.trim().isEmpty() && workerName != null && !workerName.trim().isEmpty()) {

                String email = rawEmail.trim().toLowerCase(java.util.Locale.ROOT);

                // 2.1) Validar duplicados (case-insensitive)
                if (userRepository.existsByUsernameIgnoreCase(email) || userRepository.existsByEmailIgnoreCase(email)) {
                    throw new RepositoryException("Ya existe un usuario con este correo electrónico");
                }

                // 2.2) Construir el usuario
                UserEntity user = new UserEntity();
                user.setUsername(email); // en tu login usas username; aquí lo igualamos al email
                user.setPassword(passwordEncoder.encode("123456")); // Contraseña por defecto (cámbiala luego)
                user.setName(workerName);
                user.setWorkerPosition(organization.getWorkerPosition());
                user.setEmail(email);
                user.setAcceptPolicy(true);
                user.setFirstTime(true);
                user.setOrganization(entity);

                // 2.3) Rol ORGANIZATION
                RoleEntity role = roleRepository.findByName("ORGANIZATION");
                if (role == null) {
                    throw new RepositoryException("Rol ORGANIZATION no encontrado en la base de datos");
                }
                user.setRole(role);

                // 2.4) Guardar usuario
                userRepository.save(user);
            }

        } catch (RepositoryException e) {
            // Re-lanzamos tal cual para no perder el mensaje específico
            throw e;
        } catch (Exception e) {
            // Mensaje genérico para cualquier otro fallo (FK, constraints, etc.)
            throw new RepositoryException("Error al crear la organización. " + e.getMessage());
        }
    }
    private void isOrganizationNameValid(String name) {
        if (Objects.nonNull(findOrganization(name))) {
            throw new RepositoryException("El nombre de la organización ya se encuentra registrado");
        }
    }

    @Override
    public Organization findOrganization(String name) {
        return organizationRespository.findById(name)
                .map(organizationMapper::organizationEntityToOrganization)
                .orElse(null);
    }

    @Override
    public List<Organization> getOrganizationList() {
        return organizationMapper.organizationEntityListToOrganizationList(organizationRespository.findAll());
    }

    @Override
    public void deleteOrganization(String name) {
        try {
            organizationRespository.delete(organizationMapper.organizationModelToOrganizationEntity(new Organization(name)));
        } catch (Exception e) {
            throw new RepositoryException("Error al eliminar la organización " + e.getMessage());
        }
    }

    @Override
    public void updateOrganization(Organization organization) {
        try {
            organizationRespository.save(organizationMapper.organizationModelToOrganizationEntity(organization));
        } catch (Exception e) {
            throw new RepositoryException("Error al modificar la organización. " + e.getMessage());
        }
    }
}
