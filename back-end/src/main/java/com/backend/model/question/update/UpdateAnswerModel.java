package com.backend.model.question.update;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class UpdateAnswerModel {

    private Integer id;

    @NotBlank(message = "Content is invalid")
    private String content;

    @NotNull(message = "Status is invalid")
    private Boolean status;
}
