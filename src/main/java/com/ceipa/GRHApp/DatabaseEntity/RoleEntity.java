package com.ceipa.GRHApp.DatabaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "role")
    private List<UserEntity> userEntityList = new ArrayList<>();

    public RoleEntity(String name) {
        this.name = name;
    }
}
