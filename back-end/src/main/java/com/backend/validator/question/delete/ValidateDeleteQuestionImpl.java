package com.backend.validator.question.delete;

import com.backend.dao.question.QuestionRepository;
import com.backend.service.UserService;
import com.backend.type.UserType;
import com.backend.dao.question.Question;
import com.backend.security.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Component
public class ValidateDeleteQuestionImpl implements ConstraintValidator<ValidateDeleteQuestion, Integer> {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            User user = userService.getCurrentUser();
            if (!user.getRole().equals(UserType.ADMIN.name())) {
                return question.get().getAuthor().equals(user);
            } else {
                return true;
            }
        }
        return false;

    }
}
