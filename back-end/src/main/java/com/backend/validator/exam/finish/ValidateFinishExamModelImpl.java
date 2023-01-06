package com.backend.validator.exam.finish;

import com.backend.dao.attempt.Attempt;
import com.backend.dao.attemptAnswer.AttemptAnswer;
import com.backend.dao.exam.Exam;
import com.backend.dao.exam.ExamRepository;
import com.backend.model.attempt.FinishAttemptModel;
import com.backend.model.exam.FinishExamModel;
import com.backend.security.entities.User;
import com.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Component
public class ValidateFinishExamModelImpl  implements ConstraintValidator<ValidateFinishExamModel, FinishExamModel> {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(FinishExamModel finishExamModel, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Exam> examOptional = examRepository.findById(finishExamModel.getId());
        if (examOptional.isPresent()) {
            Exam exam = examOptional.get();
            User user = userService.getCurrentUser();
            if (!exam.getAuthor().getId().equals(user.getId())
                    || exam.getResult() != null) {
                return false;
            }
            for (Attempt examAttempt : exam.getAttempts()) {
                Optional<FinishAttemptModel> attemptOptional = finishExamModel.getAttempts().stream().filter(attempt -> attempt.getAttemptId().equals(examAttempt.getId())).findFirst();
                if (!attemptOptional.isPresent()) {
                    return false;
                }
                FinishAttemptModel attempt = attemptOptional.get();
                for (Integer finishedAnswer : attempt.getAnswerList()) {
                    Optional<AttemptAnswer> answerOptional = examAttempt.getAnswers().stream().filter(answer -> answer.getId().equals(finishedAnswer)).findFirst();
                    if (!answerOptional.isPresent()) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
