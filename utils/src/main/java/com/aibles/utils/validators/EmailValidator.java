package com.aibles.utils.validators;

import com.aibles.utils.annotations.EmailConstraint;
import com.aibles.utils.util.Helpers;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {

  @Override
  public void initialize(EmailConstraint email) {}

  @Override
  public boolean isValid(String emailField, ConstraintValidatorContext context) {
    if (emailField == null || emailField.equals("")) {
      return true;
    }
    return Helpers.regexEmail(emailField);
  }
}
