package com.backend.validator.exam.start;

import com.backend.dao.exam.Exam;
import com.backend.dao.exam.ExamRepository;
import com.backend.security.entities.User;
import com.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.util.Optional;

@Component
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class ValidateStartExamImpl implements ConstraintValidator<ValidateStartExam, Object[]> {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(Object[] value, ConstraintValidatorContext constraintValidatorContext) {
        User user = userService.getCurrentUser();
        Optional<Exam> examOptional = examRepository.findByAuthorAndResult(user, null);
        return !examOptional.isPresent();
    }
}
