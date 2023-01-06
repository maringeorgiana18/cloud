package com.backend.mapper;

import com.backend.model.question.add.AddQuestionModel;
import com.backend.model.question.get.GetQuestionModel;
import com.backend.model.question.update.UpdateQuestionModel;
import com.backend.dao.question.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AnswerMapper.class})
@Component
public interface QuestionMapper {
    Question addQuestionModelToQuestion(AddQuestionModel addQuestionModel);
    Question updateQuestionModelToQuestion(UpdateQuestionModel updateQuestionModel);

    @Mapping(target = "author", source = "question.author.userName")
    GetQuestionModel getQuestionModelsFromQuestionList(Question question);

    List<GetQuestionModel> questionListToGetQuestionModelList(List<Question> questionList);
}
