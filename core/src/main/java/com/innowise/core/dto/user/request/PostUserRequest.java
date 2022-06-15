package com.innowise.core.dto.user.request;

import com.innowise.core.entity.role.Role;
import com.innowise.core.entity.user.User;
import com.innowise.core.entity.enums.Roles;
import com.innowise.core.validation.annotations.Unique;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostUserRequest {

    private String name;

    private String surname;

    private String patronymic;

    private Integer clientId;

    private Date bornDate;

    @Unique(uniqueConstraintName = "email")
    private String email;

    private String town;

    private String street;

    private String house;

    private String flat;

    @Unique(uniqueConstraintName = "login")
    private String login;

    private String password;

    private String passportNum;

    private String issuedBy;

    //private Set<Roles> userRoles;

    public User buildUser() {
        return User.builder().
                name(this.name).
                surname(this.surname).
                patronymic(this.patronymic).
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
                /*roles(this.userRoles.
                        stream().
                        map(userRole -> new Role(userRole.ordinal(), userRole)).
                        collect(Collectors.toSet())).*/
                build();
    }
}
