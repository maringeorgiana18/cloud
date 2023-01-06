package com.backend.validator.question.updateAuthor;

import com.backend.dao.question.QuestionRepository;
import com.backend.dao.question.Question;
import com.backend.security.entities.User;
import com.backend.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.util.Optional;

@Component
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class ValidateUpdateAuthorImpl implements ConstraintValidator<ValidateUpdateAuthor, Object[]> {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(Object[] value, ConstraintValidatorContext constraintValidatorContext) {
        Optional<User> user = userRepository.findById((Integer) value[1]);
        if (!user.isPresent()) {
            return false;
        }
        Optional<Question> question = questionRepository.findById((Integer) value[0]);
        return question.isPresent();
    }
}
