package com.backend.validator.user.add;

import com.backend.model.user.AddUserModel;
import com.backend.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ValidateAddUserModelImpl implements ConstraintValidator<ValidateAddUserModel, AddUserModel> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(AddUserModel addUserModel, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.findByEmail(addUserModel.getEmail()).isPresent();
    }
}
