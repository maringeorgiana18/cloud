package com.backend.model.exam;

import com.backend.model.attempt.FinishAttemptModel;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class FinishExamModel {

    @NotNull(message = "Id is required")
    private Integer id;

    private List<FinishAttemptModel> attempts;
}
