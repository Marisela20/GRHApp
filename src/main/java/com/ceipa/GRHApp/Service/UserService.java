package com.ceipa.GRHApp.Service;

import com.ceipa.GRHApp.Model.UserAccount;
import com.ceipa.GRHApp.Util.RepositoryException;

import java.util.List;

public interface UserService {

    /**
     * Guarda un usuario nuevo. Codifica el password con BCrypt.
     * @param userAccount modelo del usuario
     * @param isOrganization 1 si el rol debe ser ORGANIZATION, distinto de 1 → USER
     */
    void saveUser(UserAccount userAccount, int isOrganization);

    /**
     * Busca por username o email (case-insensitive), normalizando espacios.
     */
    UserAccount findUser(String username);

    List<UserAccount> getUserList();

    /**
     * Lista usuarios que tienen organization asignada (no null).
     */
    List<UserAccount> getOrganizationUserList();

    void deleteUser(String username) throws RepositoryException;

    void updateUser(UserAccount userAccount);

    /**
     * Devuelve info del usuario logueado actual (o null si no hay sesión).
     */
    UserAccount getCurrentUserInfo();

    void updateUserPolicy(String username);
}
