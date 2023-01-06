package com.backend.model.attempt;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class GetAttemptModel {

    private Integer id;
    private String content;
    private String description;
    private String type;
    private Boolean status;
    private String historyStatus;
    private Timestamp startDate;
    private Timestamp endDate;
    private List<GetAttemptAnswerModel> answers;

}
