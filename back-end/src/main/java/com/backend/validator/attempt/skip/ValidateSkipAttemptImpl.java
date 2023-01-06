package com.backend.validator.attempt.skip;

import com.backend.dao.attempt.Attempt;
import com.backend.dao.attempt.AttemptRepository;
import com.backend.security.entities.User;
import com.backend.service.UserService;
import com.backend.type.HistoryStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.util.Optional;

@Component
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class ValidateSkipAttemptImpl implements ConstraintValidator<ValidateSkipAttempt, Object[]> {

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(Object[] value, ConstraintValidatorContext constraintValidatorContext) {
        User user = userService.getCurrentUser();
        Optional<Attempt> attemptOptional = attemptRepository.findByAuthorAndHistoryStatus(user, HistoryStatus.STARTED.name());
        return attemptOptional.isPresent();
    }
}
