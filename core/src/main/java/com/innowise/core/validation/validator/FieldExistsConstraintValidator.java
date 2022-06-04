package com.innowise.core.validation.validator;

import com.innowise.core.entity.user.User;
import com.innowise.core.exceprtion.UserExistsException;
import com.innowise.core.validation.annotations.Unique;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FieldExistsConstraintValidator implements ConstraintValidator<Unique, String> {

    private String uniqueConstraintName;

    private final JpaSpecificationExecutor<User> repository;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.uniqueConstraintName = constraintAnnotation.uniqueConstraintName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<User> entity = repository.findAll(((root, query, criteriaBuilder) ->
            criteriaBuilder.and(criteriaBuilder.equal(root.get(uniqueConstraintName), value))
        ));
        if (entity.isEmpty())
            return true;
        else {
            String exceptionMessage = context.getDefaultConstraintMessageTemplate();
            if (exceptionMessage.equals("")) {
                exceptionMessage = new StringBuilder()
                        .append("User with ")
                        .append(uniqueConstraintName)
                        .append(" ")
                        .append(value)
                        .append(" already exists")
                        .toString();
            }
            throw new UserExistsException(exceptionMessage);
        }
    }
}
