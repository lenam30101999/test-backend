package com.aibles.utils.validators;

import com.aibles.utils.annotations.UsernameConstraint;
import com.aibles.utils.util.Helpers;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {

  @Override
  public void initialize(UsernameConstraint username) {}

  @Override
  public boolean isValid(String usernameField, ConstraintValidatorContext context) {
    if (usernameField == null || usernameField.equals("") || usernameField.length() < 6) {
      return true;
    }

    if (!Helpers.regexPhoneNumber(usernameField)) {
      return Helpers.regexEmail(usernameField);
    } else {
      return Helpers.regexPhoneNumber(usernameField);
    }
  }
}
