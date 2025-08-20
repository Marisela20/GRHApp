package com.ceipa.GRHApp.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private String description;
    private Integer points;
    private Float percentage;
    private String conclusion;

}
