package com.ceipa.GRHApp.DatabaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class SurveyItemExcludedKey implements Serializable {

    @Column(name="questionId")
    private int question;

    @Column(name="answerId")
    private int answer;

}
