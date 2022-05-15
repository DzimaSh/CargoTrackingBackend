package com.innowise.core.dto.user;

import com.innowise.core.entity.User;
import com.innowise.core.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
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

    public User buildUser() {
        return User.builder().
                name(this.name).
                surname(this.surname).
                patronymic(this.patronymic).
                clientId(this.clientId).
                bornDate(this.bornDate).
                email(this.email).
                town(this.town).
                street(this.street).
                house(this.house).
                flat(this.flat).
                login(this.login).
                password(this.password).
                passportNum(this.passportNum).
                issuedBy(this.issuedBy).
                userRoles(this.userRoles).
                build();
    }
}
