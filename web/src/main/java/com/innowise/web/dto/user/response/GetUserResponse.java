package com.innowise.web.dto.user.response;

import com.innowise.web.enums.Roles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class GetUserResponse {
    private Integer id;
    private String name;
    private String surname;
    private String patronymic;
    private Integer clientId;
    private Date bornDate;
    private String email;
    private String town;
    private String street;
    private String house;
    private String flat;
    private String login;
    private String password;
    private String passportNum;
    private String issuedBy;
    private Set<Roles> userRoles;

}
