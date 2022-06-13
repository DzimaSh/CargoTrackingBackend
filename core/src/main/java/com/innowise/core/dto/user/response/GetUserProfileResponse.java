package com.innowise.core.dto.user.response;

import com.innowise.core.entity.enums.Roles;
import com.innowise.core.entity.role.Role;
import com.innowise.core.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;
import java.util.stream.Collectors;

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

    public GetUserProfileResponse(User user) {
        id = user.getId();
        name = user.getName();
        surname = user.getSurname();
        patronymic = user.getPatronymic();
        bornDate = user.getBornDate();
        town = user.getTown();
        street = user.getStreet();
        house = user.getHouse();
        flat = user.getFlat();
        passportNum = user.getPassportNum();
        issuedBy = user.getIssuedBy();
        login = user.getLogin();
        email = user.getEmail();
        userRoles = user.getRoles().stream()
                .map(Role::getRole)
                .collect(Collectors.toSet());
    }
}
