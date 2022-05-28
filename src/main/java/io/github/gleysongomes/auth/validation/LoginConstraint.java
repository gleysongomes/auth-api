package io.github.gleysongomes.auth.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import io.github.gleysongomes.auth.validation.impl.LoginConstraintImpl;

@Documented
@Constraint(validatedBy = LoginConstraintImpl.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginConstraint {

	String message() default "Login inválido.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
