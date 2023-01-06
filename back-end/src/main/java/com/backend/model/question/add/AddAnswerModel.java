package com.backend.model.question.add;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AddAnswerModel {

    @NotBlank(message = "Content is mandatory")
    private String content;

    @NotNull(message = "Status is mandatory")
    private Boolean status;

}
