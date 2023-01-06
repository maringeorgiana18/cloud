package com.backend.validator.user.reactivateTeacher;

import com.backend.security.entities.User;
import com.backend.security.repositories.UserRepository;
import com.backend.type.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Component
public class ValidateReactivateTeacherImpl implements ConstraintValidator<ValidateReactivateTeacher, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            if (!user.get().getRole().equals(UserType.TEACHER.name())) {
                return false;
            }
            return !user.get().isEnable();
        }
        return false;
    }
}
