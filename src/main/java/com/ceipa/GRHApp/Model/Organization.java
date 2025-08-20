package com.ceipa.GRHApp.Model;

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
public class Organization {

    private int id;

    @NotEmpty
    private String name;

    private String nit;

    private String address;
    private String phone;
    private List<UserAccount> userAccountList;

    // ✅ Usamos este campo en el formulario
    private String creationDate;

    private EconomicArea economicArea;
    private String economicActivity;

    private Integer quantityEmployees;

    private String worker;
    private String workerPosition;
    private String email;

    public Organization(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nit='" + nit + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", userAccountList=" + userAccountList +
                ", creationDate='" + creationDate + '\'' +
                ", economicArea=" + economicArea +
                ", economicActivity='" + economicActivity + '\'' +
                ", quantityEmployees=" + quantityEmployees +
                ", worker='" + worker + '\'' +
                ", workerPosition='" + workerPosition + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
