package com.backend.validator.question.add;

import com.backend.model.question.add.AddAnswerModel;
import com.backend.model.question.add.AddQuestionModel;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ValidateAddQuestionModelImpl implements ConstraintValidator<ValidateAddQuestionModel, AddQuestionModel> {
    @Override
    public boolean isValid(AddQuestionModel addQuestionModel, ConstraintValidatorContext constraintValidatorContext) {
        boolean trueAnswer = false;
        boolean falseAnswer = false;
        for (AddAnswerModel answer : addQuestionModel.getAnswers()) {
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
}
