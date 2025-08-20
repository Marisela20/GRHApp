package com.ceipa.GRHApp.Model;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyItem {

    private Integer id;
    private String description;
    private Integer diagnosticId;
    private Integer order;

    // ✅ Cambiado de isQuestion a question
    private Boolean question;

    // ✅ Cambiado de isSublevel a sublevel
    private Boolean sublevel;

    private Integer section;
    private Integer score;

    private Classification classification;
    private List<SurveyAnswerOption> answerOptions;
    private SurveyItem father;

    // ✅ Nuevo campo para el tipo de pregunta (agregado para evitar error en el controlador)
    private String questionType;

    public SurveyItem(Integer id, String description, SurveyItem father, Classification classification) {
        this.id = id;
        this.description = description;
        this.father = father;
        this.classification = classification;
    }

    public SurveyItem(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public SurveyItem(Integer id) {
        this.id = id;
    }

    // ✅ Este getter extra es necesario para Thymeleaf cuando usas isSublevel
    public Boolean getIsSublevel() {
        return sublevel;
    }
}
