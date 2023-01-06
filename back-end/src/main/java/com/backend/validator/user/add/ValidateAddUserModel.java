package com.backend.validator.user.add;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(PARAMETER)
@Retention(RUNTIME)
@Constraint(validatedBy = ValidateAddUserModelImpl.class)
public @interface ValidateAddUserModel {
    String message() default "Model is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
