package com.backend.model.question.get;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class GetAnswerModel {
    private Integer id;
    private String content;
    private Boolean status;
}
