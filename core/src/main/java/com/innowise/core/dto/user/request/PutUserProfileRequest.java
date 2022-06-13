package com.innowise.core.dto.user.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PutUserProfileRequest {
    private String name;
    private String surname;
    private String patronymic;
    private Date bornDate;
    private String town;
    private String house;
    private String street;
    private String flat;

}
