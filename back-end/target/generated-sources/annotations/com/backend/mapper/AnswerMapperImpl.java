package com.backend.mapper;

import com.backend.dao.answer.Answer;
import com.backend.dao.attemptAnswer.AttemptAnswer;
import com.backend.model.attempt.GetAttemptAnswerModel;
import com.backend.model.question.add.AddAnswerModel;
import com.backend.model.question.update.UpdateAnswerModel;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-15T20:58:05+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.10 (Amazon.com Inc.)"
)
@Component
public class AnswerMapperImpl implements AnswerMapper {

    @Override
    public Answer addAnswerModelToAnswer(AddAnswerModel addAnswerModel) {
        if ( addAnswerModel == null ) {
            return null;
        }

        Answer answer = new Answer();

        answer.setContent( addAnswerModel.getContent() );
        answer.setStatus( addAnswerModel.getStatus() );

        return answer;
    }

    @Override
    public Answer updateAnswerModelToAnswer(UpdateAnswerModel updateAnswerModel) {
        if ( updateAnswerModel == null ) {
            return null;
        }

        Answer answer = new Answer();

        answer.setId( updateAnswerModel.getId() );
        answer.setContent( updateAnswerModel.getContent() );
        answer.setStatus( updateAnswerModel.getStatus() );

        return answer;
    }

    @Override
    public GetAttemptAnswerModel answerToGetAttemptAnswerModel(Answer answer) {
        if ( answer == null ) {
            return null;
        }

        GetAttemptAnswerModel getAttemptAnswerModel = new GetAttemptAnswerModel();

        getAttemptAnswerModel.setContent( answer.getContent() );

        return getAttemptAnswerModel;
    }

    @Override
    public Answer attemptAnswerToAnswer(AttemptAnswer attemptAnswer) {
        if ( attemptAnswer == null ) {
            return null;
        }

        Answer answer = new Answer();

        answer.setContent( attemptAnswer.getContent() );
        answer.setStatus( attemptAnswer.getStatus() );

        return answer;
    }
}
