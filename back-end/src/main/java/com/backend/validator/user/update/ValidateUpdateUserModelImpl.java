package com.backend.validator.user.update;

import com.backend.model.user.UpdateUserModel;
import com.backend.security.entities.User;
import com.backend.security.repositories.UserRepository;
import com.backend.service.UserService;
import com.backend.type.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Component
public class ValidateUpdateUserModelImpl implements ConstraintValidator<ValidateUpdateUserModel, UpdateUserModel> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(UpdateUserModel updateUserModel, ConstraintValidatorContext constraintValidatorContext) {
        Optional<User> user = userRepository.findById(updateUserModel.getId());
        if(user.isPresent()) {
            User currentUser = userService.getCurrentUser();
            if (!currentUser.getRole().equals(UserType.ADMIN.name()) && !user.get().getId().equals(currentUser.getId())) {
                return false;
            }
            Optional<User> userAux = userRepository.findByEmail(user.get().getEmail());
            if (userAux.isPresent()) {
                return userAux.get().getEmail().equals(user.get().getEmail());
            }
            return true;
        }
        return false;
    }
}
