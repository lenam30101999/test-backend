package com.aibles.utils.validators;


import com.aibles.utils.annotations.PhoneNumberConstraint;
import com.aibles.utils.util.Helpers;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberConstraint, String> {

  @Override
  public void initialize(PhoneNumberConstraint phoneNumber) {}

  @Override
  public boolean isValid(String phoneNumberField, ConstraintValidatorContext context) {
    if (phoneNumberField == null || phoneNumberField.equals("") || phoneNumberField.length() < 10) {
      return true;
    }
    return Helpers.regexPhoneNumber(phoneNumberField);
  }
}
