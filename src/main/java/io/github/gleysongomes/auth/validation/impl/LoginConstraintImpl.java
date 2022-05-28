package io.github.gleysongomes.auth.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.github.gleysongomes.auth.validation.LoginConstraint;

public class LoginConstraintImpl implements ConstraintValidator<LoginConstraint, String> {

	@Override
	public boolean isValid(String login, ConstraintValidatorContext context) {
		if (login == null || login.trim().isEmpty() || login.contains(" ")) {
			return false;
		} else {
			return true;
		}
	}

}
