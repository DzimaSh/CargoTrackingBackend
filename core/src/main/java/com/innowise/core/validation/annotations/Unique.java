package com.innowise.core.validation.annotations;

import com.innowise.core.validation.FieldExistsConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FieldExistsConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
    String message();
    String uniqueConstraintName();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
