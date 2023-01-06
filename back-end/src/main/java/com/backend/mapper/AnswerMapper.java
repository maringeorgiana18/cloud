package com.backend.mapper;

import com.backend.dao.answer.Answer;
import com.backend.dao.attemptAnswer.AttemptAnswer;
import com.backend.model.attempt.GetAttemptAnswerModel;
import com.backend.model.question.add.AddAnswerModel;
import com.backend.model.question.update.UpdateAnswerModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface AnswerMapper {
    Answer addAnswerModelToAnswer(AddAnswerModel addAnswerModel);
    Answer updateAnswerModelToAnswer(UpdateAnswerModel updateAnswerModel);
    GetAttemptAnswerModel answerToGetAttemptAnswerModel(Answer answer);

    @Mapping(target = "id", ignore = true)
    Answer attemptAnswerToAnswer(AttemptAnswer attemptAnswer);
}
