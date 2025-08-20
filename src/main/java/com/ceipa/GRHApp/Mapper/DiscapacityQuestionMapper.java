package com.ceipa.GRHApp.Mapper;

import com.ceipa.GRHApp.DatabaseEntity.DiscapacityAnswerOptionEntity;
import com.ceipa.GRHApp.DatabaseEntity.DiscapacityQuestionEntity;
import com.ceipa.GRHApp.Model.DiscapacityAnswerOption;
import com.ceipa.GRHApp.Model.DiscapacityQuestion;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DiscapacityQuestionMapper {

    public DiscapacityQuestion toModel(DiscapacityQuestionEntity entity) {
        if (entity == null) return null;

        DiscapacityQuestion model = new DiscapacityQuestion();
        model.setId(entity.getId());
        model.setDescription(entity.getQuestionText()); // Usa 'questionText' como descripción
        model.setQuestionType(entity.getQuestionType());
        model.setSection(entity.getSection());
        model.setOrder(entity.getOrderNum());
        model.setSurveyId(entity.getSurvey() != null ? entity.getSurvey().getId() : null);

        // Mapear las opciones de respuesta si existen
        if (entity.getOptions() != null) {
            List<DiscapacityAnswerOption> options = new ArrayList<>();
            for (DiscapacityAnswerOptionEntity optEntity : entity.getOptions()) {
                DiscapacityAnswerOption option = new DiscapacityAnswerOption();
                option.setId(optEntity.getId());
                option.setText(optEntity.getText());
                option.setScore(optEntity.getScore());
                options.add(option);
            }
            model.setAnswerOptions(options);
        }

        return model;
    }

    public List<DiscapacityQuestion> toModelList(List<DiscapacityQuestionEntity> entities) {
        List<DiscapacityQuestion> result = new ArrayList<>();
        if (entities != null) {
            for (DiscapacityQuestionEntity entity : entities) {
                result.add(toModel(entity));
            }
        }
        return result;
    }
}
