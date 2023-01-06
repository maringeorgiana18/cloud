package com.backend.validator.question.update;

import com.backend.dao.answer.Answer;
import com.backend.dao.answer.AnswerRepository;
import com.backend.dao.question.QuestionRepository;
import com.backend.model.question.update.UpdateAnswerModel;
import com.backend.model.question.update.UpdateQuestionModel;
import com.backend.dao.question.Question;
import com.backend.security.entities.User;
import com.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Component
public class ValidateUpdateQuestionModelImpl implements ConstraintValidator<ValidateUpdateQuestionModel, UpdateQuestionModel> {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public boolean isValid(UpdateQuestionModel updateQuestionModel, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Question> question = questionRepository.findById(updateQuestionModel.getId());
        if (question.isPresent()) {
            User user = userService.getCurrentUser();
            if(!question.get().getAuthor().equals(user)) {
                return false;
            }
            for (UpdateAnswerModel answer : updateQuestionModel.getAnswers()) {
                if (answer.getId() != null) {
                    Optional<Answer> answerOptional = answerRepository.findById(answer.getId());
                    if (answerOptional.isPresent()) {
                        if (!answerOptional.get().getQuestion().getId().equals(question.get().getId())) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }

            boolean trueAnswer = false;
            boolean falseAnswer = false;
            for (UpdateAnswerModel answer : updateQuestionModel.getAnswers()) {
                if (answer.getStatus() == null) {
                    return false;
                }
                if (answer.getStatus() != null && answer.getStatus().equals(Boolean.TRUE)) {
                    trueAnswer = true;
                }
                if (answer.getStatus() != null && answer.getStatus().equals(Boolean.FALSE)) {
                    falseAnswer = true;
                }
            }
            return trueAnswer & falseAnswer;
        }
        return false;
    }
}
