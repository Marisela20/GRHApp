package com.ceipa.GRHApp.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscapacityAnswerOption {
    private Integer id;
    private String text;
    private Integer score;
}
