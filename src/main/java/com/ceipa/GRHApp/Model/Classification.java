package com.ceipa.GRHApp.Model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Classification {
    private int id;
    private String name;
    private Integer totalPoints;

    public Classification(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
