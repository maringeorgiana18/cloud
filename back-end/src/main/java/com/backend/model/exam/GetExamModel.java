package com.backend.model.exam;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class GetExamModel {
    private String type;
    private Integer result;
    private Timestamp startDate;
    private Timestamp endDate;
}
