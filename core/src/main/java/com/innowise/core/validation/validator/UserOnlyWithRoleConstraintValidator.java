package com.innowise.core.validation.validator;

import com.innowise.core.dto.user.request.PostUserRequest;
import com.innowise.core.entity.enums.Roles;
import com.innowise.core.exceprtion.UserNotFoundException;
import com.innowise.core.validation.annotations.UserOnlyWithRole;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UserOnlyWithRoleConstraintValidator implements ConstraintValidator<UserOnlyWithRole, PostUserRequest> {

    private Roles requiredRole;

    @Override
    public void initialize(UserOnlyWithRole constraintAnnotation) {
        this.requiredRole = constraintAnnotation.role();
    }

    @Override
    public boolean isValid(PostUserRequest user, ConstraintValidatorContext context) {
/*        for (Roles role : user.getUserRoles()) {
            if (role.equals(requiredRole))
                return true;
        }
        String exceptionMessage = context.getDefaultConstraintMessageTemplate();
        if (exceptionMessage.equals("")) {
            exceptionMessage = new StringBuilder()
                    .append("Given user has no ")
                    .append(requiredRole.name())
                    .append(" roots.")
                    .toString();
        }
        throw new UserNotFoundException(exceptionMessage, HttpStatus.BAD_REQUEST);
    }*/

        return true;
    }
}
