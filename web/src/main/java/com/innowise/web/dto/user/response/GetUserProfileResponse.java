package com.innowise.web.dto.user.response;

import com.innowise.web.enums.Roles;
import lombok.*;

import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class GetUserProfileResponse {
    private Integer id;
    private String name;
    private String surname;
    private String patronymic;
    private Date bornDate;
    private String town;
    private String street;
    private String house;
    private String flat;
    private String passportNum;
    private String issuedBy;
    private String login;
    private String email;
    private Set<Roles> userRoles;

}
