package com.ceipa.GRHApp.DatabaseEntity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="conclusions", schema="grhdatabase")
public class ConclusionEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="classificationId")
    ClassificationEntity classification;

    @Column(name="minValue")
    private Integer minValue;

    @Column(name="maxValue")
    private Integer maxValue;

    @Column(name="description")
    private String description;

}
