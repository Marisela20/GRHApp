package com.ceipa.GRHApp.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class LevelPosition {

    private int id;

    // ✅ Esta propiedad debe existir si en el HTML usas lp.name
    private String name;

    // Puedes tener más campos si quieres
    // private String code;
    // private String description;

    @Override
    public String toString() {
        return "LevelPosition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
