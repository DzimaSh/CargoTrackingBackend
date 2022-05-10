package com.innowise.web.dto.response;

import com.innowise.core.entity.User;
import com.innowise.core.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
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
    private String passportNumber;
    private String passportIssuedBy;
    private UserRole[] roles;

    public GetUserResponse(User user) {
        id = user.getId();
        name = user.getName();
        surname = user.getSurname();
        patronymic = user.getPatronymic();
        clientId = user.getClientId();
        bornDate = user.getBornDate();
        email = user.getEmail();
        town = user.getTown();
        street = user.getStreet();
        house = user.getHouse();
        flat = user.getFlat();
        login = user.getLogin();
        password = user.getPassword();
        passportNumber = user.getPassportNumber();
        passportIssuedBy = user.getPassportIssuedBy();
        roles = user.getRoles();
    }
}
