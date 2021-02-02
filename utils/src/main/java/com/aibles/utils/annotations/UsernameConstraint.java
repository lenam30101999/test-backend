package com.aibles.utils.annotations;


import com.aibles.utils.validators.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameConstraint {
  String message() default "Wrong username format!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
