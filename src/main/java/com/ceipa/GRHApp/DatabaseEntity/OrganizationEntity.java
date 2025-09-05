package com.ceipa.GRHApp.DatabaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="organization")
public class OrganizationEntity {

    @Id
    @Column(name="name")
    private String name;

    @Column(name="address")
    private String address;

    @Column(name="phone")
    private String phone;

    @OneToMany(mappedBy="organization")
    private List<UserEntity> userEntity;

    @Column(name="creationDate")
    private String creationDate;

    @ManyToOne
    @JoinColumn(name="economicAreaId")
    private EconomicAreaEntity economicArea;

    @Column(name="economicActivity")
    private String economicActivity;

    @Column(name="quantityEmployees")
    private Integer quantityEmployees;

    @Column(name="worker")
    private String worker;

    @Column(name="workerPosition")
    private String workerPosition;

    @Column(name="email")
    private String email;


    public OrganizationEntity(String name) {
        this.name = name;
    }

    public OrganizationEntity(String name, String address, String phone) {
        this.name=name;
        this.address = address;
        this.phone=phone;

    }

    @Override
    public String toString() {
        return "OrganizationEntity{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}