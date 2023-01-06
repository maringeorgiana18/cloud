package com.backend.model.attempt;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class FinishAttemptModel {

    @NotNull(message = "Invalid attempt")
    private Integer attemptId;

    @NotNull(message = "Invalid answer list")
    private List<Integer> answerList;
}
