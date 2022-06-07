package com.innowise.web.dto.user.request;

import com.innowise.web.enums.Roles;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostUserRequest {

    @Size(max = 20, message = "Name is too long")
    private String name;

    @NotEmpty(message = "Surname is required")
    @Size(max = 20, message = "Surname is too long")
    private String surname;

    @Size(max = 20, message = "Patronymic is too long")
    private String patronymic;

    private Integer clientId;

    private Date bornDate;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email isn't valid")
    @Size(max = 50, message = "Email is too long")
    private String email;

    @Size(max = 20, message = "Town name length is too long")
    private String town;

    @Size(max = 20, message = "Street name length is too long")
    private String street;

    @Size(max = 5, message = "House number is too long")
    private String house;

    @Size(max = 5, message = "Flat number is too long")
    private String flat;

    @NotEmpty(message = "Login is required")
    @Size(min = 5, message = "Login is too short")
    @Size(max = 15, message = "Login is too long")
    private String login;

    @NotEmpty(message = "Password is required")
    @Size(min = 5, message = "Password is too short")
    @Size(max = 72, message = "Password is too long")
    private String password;

    @Size(max = 30, message = "Passport number size is too long")
    private String passportNum;

    @Size(max = 50, message = "Establishment that have issued the passport name is too long")
    private String issuedBy;

    @NotEmpty(message = "at least one role is required")
    private Set<Roles> userRoles;

}
