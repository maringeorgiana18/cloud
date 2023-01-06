package com.backend.mapper;

import com.backend.dao.answer.Answer;
import com.backend.dao.attemptAnswer.AttemptAnswer;
import com.backend.model.attempt.GetAttemptAnswerModel;
import com.backend.model.attempt.StartAnswerModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface AttemptAnswerMapper {
    GetAttemptAnswerModel answerToGetAttemptAnswerModel(AttemptAnswer attemptAnswer);
    StartAnswerModel answerToStartAnswerAnswerModel(AttemptAnswer attemptAnswer);

    @Mapping(target = "id", ignore = true)
    AttemptAnswer answerToAttemptAnswer(Answer answer);
}
