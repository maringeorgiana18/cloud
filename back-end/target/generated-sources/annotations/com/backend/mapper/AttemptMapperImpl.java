package com.backend.mapper;

import com.backend.dao.answer.Answer;
import com.backend.dao.attempt.Attempt;
import com.backend.dao.attemptAnswer.AttemptAnswer;
import com.backend.dao.question.Question;
import com.backend.model.attempt.GetAttemptAnswerModel;
import com.backend.model.attempt.GetAttemptModel;
import com.backend.model.attempt.StartAnswerModel;
import com.backend.model.attempt.StartAttemptModel;
import com.backend.security.entities.User;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-15T20:58:05+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.10 (Amazon.com Inc.)"
)
@Component
public class AttemptMapperImpl implements AttemptMapper {

    @Autowired
    private AttemptAnswerMapper attemptAnswerMapper;

    @Override
    public Attempt questionToAttempt(Question question, User author, Timestamp startDate, String historyStatus) {
        if ( question == null && author == null && startDate == null && historyStatus == null ) {
            return null;
        }

        Attempt attempt = new Attempt();

        if ( question != null ) {
            attempt.setAnswers( answerListToAttemptAnswerList( question.getAnswers() ) );
            attempt.setQuestion( question );
            attempt.setType( question.getType() );
            attempt.setDescription( question.getDescription() );
            attempt.setContent( question.getContent() );
        }
        if ( author != null ) {
            attempt.setAuthor( author );
        }
        if ( startDate != null ) {
            attempt.setStartDate( startDate );
        }
        if ( historyStatus != null ) {
            attempt.setHistoryStatus( historyStatus );
        }

        return attempt;
    }

    @Override
    public StartAttemptModel attemptToStartAttemptModel(Attempt attempt) {
        if ( attempt == null ) {
            return null;
        }

        StartAttemptModel startAttemptModel = new StartAttemptModel();

        startAttemptModel.setId( attempt.getId() );
        startAttemptModel.setContent( attempt.getContent() );
        startAttemptModel.setDescription( attempt.getDescription() );
        startAttemptModel.setType( attempt.getType() );
        startAttemptModel.setAnswers( attemptAnswerListToStartAnswerModelList( attempt.getAnswers() ) );

        return startAttemptModel;
    }

    @Override
    public GetAttemptModel attemptToGetAttemptModel(Attempt attempt) {
        if ( attempt == null ) {
            return null;
        }

        GetAttemptModel getAttemptModel = new GetAttemptModel();

        getAttemptModel.setId( attempt.getId() );
        getAttemptModel.setContent( attempt.getContent() );
        getAttemptModel.setDescription( attempt.getDescription() );
        getAttemptModel.setType( attempt.getType() );
        getAttemptModel.setStatus( attempt.getStatus() );
        getAttemptModel.setHistoryStatus( attempt.getHistoryStatus() );
        getAttemptModel.setStartDate( attempt.getStartDate() );
        getAttemptModel.setEndDate( attempt.getEndDate() );
        getAttemptModel.setAnswers( attemptAnswerListToGetAttemptAnswerModelList( attempt.getAnswers() ) );

        return getAttemptModel;
    }

    @Override
    public List<GetAttemptModel> attemptListToGetAttemptModelList(List<Attempt> attempt) {
        if ( attempt == null ) {
            return null;
        }

        List<GetAttemptModel> list = new ArrayList<GetAttemptModel>( attempt.size() );
        for ( Attempt attempt1 : attempt ) {
            list.add( attemptToGetAttemptModel( attempt1 ) );
        }

        return list;
    }

    @Override
    public List<StartAttemptModel> attemptListToStartAttemptModelList(List<Attempt> attempt) {
        if ( attempt == null ) {
            return null;
        }

        List<StartAttemptModel> list = new ArrayList<StartAttemptModel>( attempt.size() );
        for ( Attempt attempt1 : attempt ) {
            list.add( attemptToStartAttemptModel( attempt1 ) );
        }

        return list;
    }

    protected List<AttemptAnswer> answerListToAttemptAnswerList(List<Answer> list) {
        if ( list == null ) {
            return null;
        }

        List<AttemptAnswer> list1 = new ArrayList<AttemptAnswer>( list.size() );
        for ( Answer answer : list ) {
            list1.add( attemptAnswerMapper.answerToAttemptAnswer( answer ) );
        }

        return list1;
    }

    protected List<StartAnswerModel> attemptAnswerListToStartAnswerModelList(List<AttemptAnswer> list) {
        if ( list == null ) {
            return null;
        }

        List<StartAnswerModel> list1 = new ArrayList<StartAnswerModel>( list.size() );
        for ( AttemptAnswer attemptAnswer : list ) {
            list1.add( attemptAnswerMapper.answerToStartAnswerAnswerModel( attemptAnswer ) );
        }

        return list1;
    }

    protected List<GetAttemptAnswerModel> attemptAnswerListToGetAttemptAnswerModelList(List<AttemptAnswer> list) {
        if ( list == null ) {
            return null;
        }

        List<GetAttemptAnswerModel> list1 = new ArrayList<GetAttemptAnswerModel>( list.size() );
        for ( AttemptAnswer attemptAnswer : list ) {
            list1.add( attemptAnswerMapper.answerToGetAttemptAnswerModel( attemptAnswer ) );
        }

        return list1;
    }
}
