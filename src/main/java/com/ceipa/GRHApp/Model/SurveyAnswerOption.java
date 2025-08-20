package com.ceipa.GRHApp.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SurveyAnswerOption {

    private int id;
    private String text;
    private int score;

    public SurveyAnswerOption(int id, String text, int score) {
        this.id = id;
        this.text = text;
        this.score = score;
    }

    @Override
    public String toString() {
        return "SurveyAnswerOption{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", score=" + score +
                '}';
    }
}
