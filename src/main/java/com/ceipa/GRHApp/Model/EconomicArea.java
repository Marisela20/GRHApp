package com.ceipa.GRHApp.Model;

import lombok.*;
import javax.persistence.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "economic_area") // nombre real de la tabla
public class EconomicArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // <-- si tu PK tiene otro nombre, cámbialo aquí
    private Integer id;

    // La propiedad Java sigue llamándose "name", pero mapeamos a la columna "description"
    @Column(name = "description", nullable = false, length = 150)
    private String name;

    public EconomicArea(int id) { this.id = id; }
}
