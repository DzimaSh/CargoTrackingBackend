package com.innowise.web.dto.client.request;

import com.innowise.web.enums.ClientSubjectStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostClientRequest {

    @NotBlank(message = "company name is required")
    @Size(max = 30, message = "company name size is too big")
    private String name;

    @NotNull(message = "company status is required")
    private ClientSubjectStatus status;

    @Valid
    @NotNull(message = "please input admin user information")
    private PostAdminUserRequest adminInfo;

    @Getter
    @Setter
    public class PostAdminUserRequest {
        @Size(max = 20, message = "Name is too long")
        private String name;

        @NotEmpty(message = "Surname is required")
        @Size(max = 20, message = "Surname is too long")
        private String surname;

        @Size(max = 20, message = "Patronymic is too long")
        private String patronymic;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
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
    }
}
