package com.innowise.web.dto.user.request;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PutUserProfileRequest {
    @Size(max = 20, message = "Name is too long")
    private String name;

    @NotEmpty(message = "Surname is required")
    @Size(max = 20, message = "Surname is too long")
    private String surname;

    @Size(max = 20, message = "Patronymic is too long")
    private String patronymic;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bornDate;

    @Size(max = 20, message = "Town name length is too long")
    private String town;

    @Size(max = 20, message = "Street name length is too long")
    private String street;

    @Size(max = 5, message = "House number is too long")
    private String house;

    @Size(max = 5, message = "Flat number is too long")
    private String flat;

}
