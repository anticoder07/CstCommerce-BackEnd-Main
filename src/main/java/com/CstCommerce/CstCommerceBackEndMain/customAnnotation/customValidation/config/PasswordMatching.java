package com.CstCommerce.CstCommerceBackEndMain.customAnnotation.customValidation.config;

import com.CstCommerce.CstCommerceBackEndMain.customAnnotation.customValidation.logic.PasswordMatchingValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = PasswordMatchingValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatching {
  String password();

  String confirmPassword();

  String message() default "Passwords must match!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}