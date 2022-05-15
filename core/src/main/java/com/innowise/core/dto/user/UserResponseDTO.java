package com.innowise.core.dto.user;

import com.innowise.core.entity.User;
import com.innowise.core.entity.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDTO {
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
    private Set<UserRole> userRoles;

    public UserResponseDTO(User user) {
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
        passportNum = user.getPassportNum();
        issuedBy = user.getIssuedBy();
        userRoles = user.getUserRoles();
    }
}
