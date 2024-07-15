package net.mglsk.htmx.data.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = ContactUniqueEmailExceptCurrentValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ContactUniqueEmailExceptCurrent {
    String message() default "This email must be unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String idField() default "id";
    String emailField() default "email";}
