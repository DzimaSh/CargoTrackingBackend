package com.innowise.core.validation.annotations;

import com.innowise.core.entity.enums.Roles;
import com.innowise.core.validation.validator.UserOnlyWithRoleConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserOnlyWithRoleConstraintValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UserOnlyWithRole {
    String message() default "";
    Roles role();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
