package com.ceipa.GRHApp.Model;

import com.ceipa.GRHApp.DatabaseEntity.UserEntity; // ⬅️ Asegúrate de importar esto
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {

    private Integer id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private Role role;
    private Organization organization;
    private List<Survey> surveyList;
    private LevelPosition levelPosition;
    private Boolean firstTime;
    private String workerPosition;
    private Boolean acceptPolicy;
    private String email;

    // ✅ NUEVO: campo que referencia la entidad original
    private UserEntity entity;

    public UserAccount(String username) {
        this.username = username;
        this.organization = new Organization();
        this.role = new Role();
    }

    public UserAccount(String name, String username, String password, Role role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UserAccount(Integer id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserApp{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", organization=" + organization +
                '}';
    }
}
