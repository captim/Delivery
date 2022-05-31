package com.dumanskyi.delivery.entities.validators.annotations;

import com.dumanskyi.delivery.entities.validators.UniqueFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = UniqueFieldValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
    String message() default "Must be unique";
    DatabaseField databaseField() default DatabaseField.USERNAME;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
