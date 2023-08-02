package com.innowise.core.dto.client.request;

import com.innowise.core.entity.client.Client;
import com.innowise.core.entity.enums.ClientSubjectStatus;
import com.innowise.core.entity.enums.Roles;
import com.innowise.core.entity.role.Role;
import com.innowise.core.entity.user.User;
import com.innowise.core.validation.annotations.Unique;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostClientRequest {

    private String name;

    private ClientSubjectStatus status;

    private PostAdminUserRequest adminInfo;

    public Client buildClientFromRequest() {
        return Client.builder()
                .name(this.name)
                .subjectStatus(this.status)
                .adminInfo(this.adminInfo.buildAdmin())
                .deleteDate(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    @Getter
    @Setter
    public class PostAdminUserRequest {
        private String name;

        private String surname;

        private String patronymic;

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

        public User buildAdmin() {
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
                    roles(Set.of(new Role(Roles.ADMIN.ordinal(), Roles.ADMIN))).
                    build();
        }
    }
}
