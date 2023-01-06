package com.backend.model.question.add;

import com.backend.type.QuestionType;
import com.backend.validator.valueOfEnum.ValueOfEnum;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AddQuestionModel {

    @NotBlank(message = "Content is invalid")
    private String content;

    @NotBlank(message = "Type is invalid")
    @ValueOfEnum(enumClass = QuestionType.class, message = "Type is invalid")
    private String type;

    @NotBlank(message = "Description is invalid")
    private String description;

    @NotEmpty(message = "Answers is invalid")
    @Valid
    private List<AddAnswerModel> answers;

}
