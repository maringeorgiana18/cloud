package com.backend.mapper;

import com.backend.dao.answer.Answer;
import com.backend.dao.attemptAnswer.AttemptAnswer;
import com.backend.model.attempt.GetAttemptAnswerModel;
import com.backend.model.attempt.StartAnswerModel;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-15T20:58:05+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.10 (Amazon.com Inc.)"
)
@Component
public class AttemptAnswerMapperImpl implements AttemptAnswerMapper {

    @Override
    public GetAttemptAnswerModel answerToGetAttemptAnswerModel(AttemptAnswer attemptAnswer) {
        if ( attemptAnswer == null ) {
            return null;
        }

        GetAttemptAnswerModel getAttemptAnswerModel = new GetAttemptAnswerModel();

        getAttemptAnswerModel.setContent( attemptAnswer.getContent() );

        return getAttemptAnswerModel;
    }

    @Override
    public StartAnswerModel answerToStartAnswerAnswerModel(AttemptAnswer attemptAnswer) {
        if ( attemptAnswer == null ) {
            return null;
        }

        StartAnswerModel startAnswerModel = new StartAnswerModel();

        startAnswerModel.setId( attemptAnswer.getId() );
        startAnswerModel.setContent( attemptAnswer.getContent() );

        return startAnswerModel;
    }

    @Override
    public AttemptAnswer answerToAttemptAnswer(Answer answer) {
        if ( answer == null ) {
            return null;
        }

        AttemptAnswer attemptAnswer = new AttemptAnswer();

        attemptAnswer.setContent( answer.getContent() );
        attemptAnswer.setStatus( answer.getStatus() );

        return attemptAnswer;
    }
}
