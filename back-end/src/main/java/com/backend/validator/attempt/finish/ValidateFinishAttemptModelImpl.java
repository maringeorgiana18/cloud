package com.backend.validator.attempt.finish;

import com.backend.dao.attempt.Attempt;
import com.backend.dao.attempt.AttemptRepository;
import com.backend.dao.attemptAnswer.AttemptAnswer;
import com.backend.model.attempt.FinishAttemptModel;
import com.backend.security.entities.User;
import com.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Component
public class ValidateFinishAttemptModelImpl implements ConstraintValidator<ValidateFinishAttemptModel, FinishAttemptModel> {

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(FinishAttemptModel finishAttemptModel, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Attempt> attemptOptional = attemptRepository.findById(finishAttemptModel.getAttemptId());
        if (attemptOptional.isPresent()) {
            User user = userService.getCurrentUser();
            Attempt attempt = attemptOptional.get();
            if (!attempt.getAuthor().getId().equals(user.getId())) {
                return false;
            }
            for(Integer finishAnswer : finishAttemptModel.getAnswerList()) {
                Optional<AttemptAnswer> answerOptional = attempt.getAnswers().stream().filter(answer -> answer.getId().equals(finishAnswer)).findFirst();
                if (!answerOptional.isPresent()) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
