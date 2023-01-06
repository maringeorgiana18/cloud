package com.backend.mapper;

import com.backend.dao.attempt.Attempt;
import com.backend.dao.question.Question;
import com.backend.model.attempt.GetAttemptModel;
import com.backend.model.attempt.StartAttemptModel;
import com.backend.security.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Mapper(componentModel = "spring", uses = {AttemptAnswerMapper.class})
@Component
public interface AttemptMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "author", target = "author")
    @Mapping(source = "question", target = "question")
    @Mapping(source = "question.answers", target = "answers")
    Attempt questionToAttempt(Question question, User author, Timestamp startDate, String historyStatus);

    StartAttemptModel attemptToStartAttemptModel(Attempt attempt);

    GetAttemptModel attemptToGetAttemptModel(Attempt attempt);

    List<GetAttemptModel> attemptListToGetAttemptModelList(List<Attempt> attempt);

    List<StartAttemptModel> attemptListToStartAttemptModelList(List<Attempt> attempt);

}
