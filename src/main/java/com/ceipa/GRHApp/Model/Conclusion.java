package com.ceipa.GRHApp.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Conclusion {

    private int id;

    Classification classification;

    private Integer minValue;

    private Integer maxValue;

    private String description;

}
