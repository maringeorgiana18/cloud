package com.backend.model.question.get;

import lombok.*;

import java.sql.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GetQuestionModel {

    private Integer id;
    private String content;
    private String type;
    private String description;
    private String author;
    private Date creationDate;
    private List<GetAnswerModel> answers;

}
