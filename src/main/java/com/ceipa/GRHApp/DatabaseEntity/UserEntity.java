package com.ceipa.GRHApp.DatabaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "`user`") // ← comillas invertidas para nombre reservado
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")        // explícito
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    private String name;
    private String password;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private OrganizationEntity organization;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "level_position_id")
    private LevelPositionEntity levelPosition;

    @Column(name = "first_time")
    private Boolean firstTime;

    @Column(name = "worker_position")
    private String workerPosition;

    @Column(name = "accept_policy")
    private Boolean acceptPolicy;

    private String email;
}
