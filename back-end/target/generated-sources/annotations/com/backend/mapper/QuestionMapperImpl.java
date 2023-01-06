package com.backend.mapper;

import com.backend.dao.answer.Answer;
import com.backend.dao.question.Question;
import com.backend.model.question.add.AddAnswerModel;
import com.backend.model.question.add.AddQuestionModel;
import com.backend.model.question.get.GetAnswerModel;
import com.backend.model.question.get.GetQuestionModel;
import com.backend.model.question.update.UpdateAnswerModel;
import com.backend.model.question.update.UpdateQuestionModel;
import com.backend.security.entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-15T20:58:04+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.10 (Amazon.com Inc.)"
)
@Component
public class QuestionMapperImpl implements QuestionMapper {

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public Question addQuestionModelToQuestion(AddQuestionModel addQuestionModel) {
        if ( addQuestionModel == null ) {
            return null;
        }

        Question question = new Question();

        question.setContent( addQuestionModel.getContent() );
        question.setType( addQuestionModel.getType() );
        question.setDescription( addQuestionModel.getDescription() );
        question.setAnswers( addAnswerModelListToAnswerList( addQuestionModel.getAnswers() ) );

        return question;
    }

    @Override
    public Question updateQuestionModelToQuestion(UpdateQuestionModel updateQuestionModel) {
        if ( updateQuestionModel == null ) {
            return null;
        }

        Question question = new Question();

        question.setId( updateQuestionModel.getId() );
        question.setContent( updateQuestionModel.getContent() );
        question.setType( updateQuestionModel.getType() );
        question.setDescription( updateQuestionModel.getDescription() );
        question.setAnswers( updateAnswerModelListToAnswerList( updateQuestionModel.getAnswers() ) );

        return question;
    }

    @Override
    public GetQuestionModel getQuestionModelsFromQuestionList(Question question) {
        if ( question == null ) {
            return null;
        }

        GetQuestionModel getQuestionModel = new GetQuestionModel();

        getQuestionModel.setAuthor( questionAuthorUserName( question ) );
        getQuestionModel.setId( question.getId() );
        getQuestionModel.setContent( question.getContent() );
        getQuestionModel.setType( question.getType() );
        getQuestionModel.setDescription( question.getDescription() );
        getQuestionModel.setCreationDate( question.getCreationDate() );
        getQuestionModel.setAnswers( answerListToGetAnswerModelList( question.getAnswers() ) );

        return getQuestionModel;
    }

    @Override
    public List<GetQuestionModel> questionListToGetQuestionModelList(List<Question> questionList) {
        if ( questionList == null ) {
            return null;
        }

        List<GetQuestionModel> list = new ArrayList<GetQuestionModel>( questionList.size() );
        for ( Question question : questionList ) {
            list.add( getQuestionModelsFromQuestionList( question ) );
        }

        return list;
    }

    protected List<Answer> addAnswerModelListToAnswerList(List<AddAnswerModel> list) {
        if ( list == null ) {
            return null;
        }

        List<Answer> list1 = new ArrayList<Answer>( list.size() );
        for ( AddAnswerModel addAnswerModel : list ) {
            list1.add( answerMapper.addAnswerModelToAnswer( addAnswerModel ) );
        }

        return list1;
    }

    protected List<Answer> updateAnswerModelListToAnswerList(List<UpdateAnswerModel> list) {
        if ( list == null ) {
            return null;
        }

        List<Answer> list1 = new ArrayList<Answer>( list.size() );
        for ( UpdateAnswerModel updateAnswerModel : list ) {
            list1.add( answerMapper.updateAnswerModelToAnswer( updateAnswerModel ) );
        }

        return list1;
    }

    private String questionAuthorUserName(Question question) {
        if ( question == null ) {
            return null;
        }
        User author = question.getAuthor();
        if ( author == null ) {
            return null;
        }
        String userName = author.getUserName();
        if ( userName == null ) {
            return null;
        }
        return userName;
    }

    protected GetAnswerModel answerToGetAnswerModel(Answer answer) {
        if ( answer == null ) {
            return null;
        }

        GetAnswerModel getAnswerModel = new GetAnswerModel();

        getAnswerModel.setId( answer.getId() );
        getAnswerModel.setContent( answer.getContent() );
        getAnswerModel.setStatus( answer.getStatus() );

        return getAnswerModel;
    }

    protected List<GetAnswerModel> answerListToGetAnswerModelList(List<Answer> list) {
        if ( list == null ) {
            return null;
        }

        List<GetAnswerModel> list1 = new ArrayList<GetAnswerModel>( list.size() );
        for ( Answer answer : list ) {
            list1.add( answerToGetAnswerModel( answer ) );
        }

        return list1;
    }
}
