package com.backend.model.exam;

import com.backend.model.attempt.StartAttemptModel;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class StartExamModel {
    private Integer id;
    private List<StartAttemptModel> attempts;
}
