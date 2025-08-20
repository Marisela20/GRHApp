package com.ceipa.GRHApp.DatabaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class UserAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String username;

    private String password;

    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private OrganizationEntity organization;
}
