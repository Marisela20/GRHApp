package com.ceipa.GRHApp.Service;

import com.ceipa.GRHApp.Model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        UserAccount userAccount = userService.findUser(usernameOrEmail);

        if (userAccount == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + usernameOrEmail);
        }

        // ✅ Asegura prefijo ROLE_
        String roleName = userAccount.getRole() != null ? userAccount.getRole().getName() : "USER";
        String authorityName = roleName != null && roleName.startsWith("ROLE_")
                ? roleName
                : "ROLE_" + (roleName == null ? "USER" : roleName);

        GrantedAuthority authority = new SimpleGrantedAuthority(authorityName);

        return new org.springframework.security.core.userdetails.User(
                userAccount.getUsername(),   // compara con name="username" del form
                userAccount.getPassword(),   // BCrypt
                Collections.singletonList(authority)
        );
    }
}
