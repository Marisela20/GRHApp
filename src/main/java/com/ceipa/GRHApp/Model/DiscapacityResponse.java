package com.ceipa.GRHApp.Model;

import lombok.Data;
import java.util.Date;

@Data
public class DiscapacityResponse {
    private int id;
    private DiscapacityQuestion question;
    private DiscapacityAnswerOption answerOption;
    private String manualAnswer; // Se mapeará desde manualText
    private Date createdAt;
}
