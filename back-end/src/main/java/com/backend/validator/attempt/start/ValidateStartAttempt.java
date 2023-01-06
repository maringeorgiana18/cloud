package com.backend.validator.attempt.start;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, CONSTRUCTOR})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidateStartAttemptImpl.class)
public @interface ValidateStartAttempt {
    String message() default "Model is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
