package com.ceipa.GRHApp.Service;

import com.ceipa.GRHApp.DatabaseEntity.OrganizationEntity;
import com.ceipa.GRHApp.DatabaseEntity.RoleEntity;
import com.ceipa.GRHApp.DatabaseEntity.UserEntity;
import com.ceipa.GRHApp.Model.Organization;
import com.ceipa.GRHApp.Model.Role;
import com.ceipa.GRHApp.Model.UserAccount;
import com.ceipa.GRHApp.Repository.UserRepository;
import com.ceipa.GRHApp.Util.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final String ROLE_USER = "USER";
    private static final String ROLE_ORGANIZATION = "ORGANIZATION";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ===================== PUBLIC API =====================

    @Override
    @Transactional
    public void saveUser(UserAccount userAccount, int isOrganization) {
        Objects.requireNonNull(userAccount, "userAccount no puede ser null");

        // Normalización de username/email
        String username = normalize(userAccount.getUsername());
        String email = normalize(userAccount.getEmail());

        if (isEmpty(username)) {
            throw new IllegalArgumentException("El username es obligatorio");
        }
        if (isEmpty(email)) {
            // si en tu flujo username = correo, reutiliza
            email = username;
        }

        // Duplicados
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new IllegalStateException("Ya existe un usuario con ese username");
        }
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new IllegalStateException("Ya existe un usuario con ese correo electrónico");
        }

        // Rol
        String desiredRoleName = (isOrganization == 1) ? ROLE_ORGANIZATION : ROLE_USER;
        RoleEntity roleEntity = userRepository.findRoleEntityByName(desiredRoleName);
        if (roleEntity == null) {
            roleEntity = userRepository.findRoleEntityByName(ROLE_USER);
        }
        if (roleEntity == null) {
            throw new IllegalStateException("No se encontró rol válido (USER/ORGANIZATION)");
        }

        // Organización (FK a organization.name) — opcional
        OrganizationEntity orgEntity = null;
        String orgName = extractOrganizationName(userAccount);
        if (!isEmpty(orgName)) {
            orgEntity = userRepository.findOrganizationEntityByName(orgName);
            if (orgEntity == null) {
                throw new IllegalArgumentException("La organización '" + orgName + "' no existe");
            }
        }

        // Password
        String rawPassword = userAccount.getPassword();
        if (isEmpty(rawPassword)) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        String encoded = passwordEncoder.encode(rawPassword);

        // Construir entidad y guardar
        UserEntity entity = new UserEntity();
        entity.setUsername(username);
        entity.setName(userAccount.getName());
        entity.setPassword(encoded);
        entity.setOrganization(orgEntity);
        entity.setEmail(email);
        entity.setRole(roleEntity);
        entity.setFirstTime(Boolean.FALSE);
        entity.setAcceptPolicy(Boolean.TRUE);
        entity.setWorkerPosition(userAccount.getWorkerPosition());

        userRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public UserAccount findUser(String usernameOrEmail) {
        String key = normalize(usernameOrEmail);
        if (isEmpty(key)) return null;

        Optional<UserEntity> opt = userRepository.findByUsernameIgnoreCase(key);
        if (!opt.isPresent()) {
            opt = userRepository.findByEmailIgnoreCase(key);
        }
        return opt.map(this::toModel).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAccount> getUserList() {
        return userRepository.findAll().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAccount> getOrganizationUserList() {
        return userRepository.findAllWithOrganization().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteUser(String username) throws RepositoryException {
        String key = normalize(username);
        if (isEmpty(key)) {
            throw new RepositoryException("Username inválido");
        }
        Optional<UserEntity> opt = userRepository.findByUsernameIgnoreCase(key);
        if (!opt.isPresent()) {
            throw new RepositoryException("No existe el usuario: " + username);
        }
        userRepository.delete(opt.get());
    }

    @Override
    @Transactional
    public void updateUser(UserAccount userAccount) {
        Objects.requireNonNull(userAccount, "userAccount no puede ser null");
        String username = normalize(userAccount.getUsername());
        if (isEmpty(username)) {
            throw new IllegalArgumentException("El username es obligatorio para actualizar");
        }

        UserEntity entity = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado: " + username));

        // Campos editables
        if (!isEmpty(userAccount.getName())) entity.setName(userAccount.getName());
        if (!isEmpty(userAccount.getEmail())) entity.setEmail(normalize(userAccount.getEmail()));
        if (!isEmpty(userAccount.getWorkerPosition())) entity.setWorkerPosition(userAccount.getWorkerPosition());

        // Si enviaron nueva contraseña
        if (!isEmpty(userAccount.getPassword())) {
            entity.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        }

        // Organización si cambia
        String orgName = extractOrganizationName(userAccount);
        if (!isEmpty(orgName)) {
            OrganizationEntity org = userRepository.findOrganizationEntityByName(orgName);
            if (org == null) {
                throw new IllegalArgumentException("La organización '" + orgName + "' no existe");
            }
            entity.setOrganization(org);
        }

        // Rol si cambia
        if (userAccount.getRole() != null && !isEmpty(userAccount.getRole().getName())) {
            String rn = userAccount.getRole().getName().trim();
            if (rn.startsWith("ROLE_")) rn = rn.substring(5);
            RoleEntity re = userRepository.findRoleEntityByName(rn);
            if (re == null) {
                throw new IllegalArgumentException("Rol no válido: " + userAccount.getRole().getName());
            }
            entity.setRole(re);
        }

        userRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public UserAccount getCurrentUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return null;
        String name = auth.getName();
        return findUser(name);
    }

    @Override
    @Transactional
    public void updateUserPolicy(String username) {
        String key = normalize(username);
        if (isEmpty(key)) return;
        userRepository.updateUserPolicy(key);
    }

    // ===================== HELPERS =====================

    private String normalize(String s) {
        return (s == null) ? null : s.trim().toLowerCase(Locale.ROOT);
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    private String extractOrganizationName(UserAccount ua) {
        if (ua == null) return null;
        try {
            Organization org = ua.getOrganization();
            if (org != null && !isEmpty(org.getName())) {
                return org.getName().trim();
            }
        } catch (NoSuchMethodError | RuntimeException ignored) {}
        return null;
    }

    private UserAccount toModel(UserEntity e) {
        if (e == null) return null;

        UserAccount m = new UserAccount();
        m.setId(e.getId());
        m.setUsername(e.getUsername());
        m.setName(e.getName());
        m.setPassword(e.getPassword());
        m.setEmail(e.getEmail());
        m.setWorkerPosition(e.getWorkerPosition());

        if (e.getOrganization() != null) {
            Organization org = new Organization();
            org.setName(e.getOrganization().getName());
            m.setOrganization(org);
        } else {
            m.setOrganization(null);
        }

        String roleName = (e.getRole() != null && e.getRole().getName() != null)
                ? e.getRole().getName()
                : "USER";
        m.setRole(new Role(roleName));

        return m;
    }
}
