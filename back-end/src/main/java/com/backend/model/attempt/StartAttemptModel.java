package com.backend.model.attempt;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class StartAttemptModel {

    private Integer id;
    private String content;
    private String description;
    private String type;
    private List<StartAnswerModel> answers;

}
