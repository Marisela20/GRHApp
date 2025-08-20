package com.ceipa.GRHApp.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    // 🔧 Cambiado a wrapper para permitir null y evitar fallos en selects
    private Integer id;
    private String name;
    private List<UserAccount> userAccountList = new ArrayList<>();

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
