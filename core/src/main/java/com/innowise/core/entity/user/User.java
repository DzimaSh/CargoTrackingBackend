package com.innowise.core.entity.user;

import com.innowise.core.entity.role.Role;
import lombok.*;
import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity(name = "users")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String surname;

    private String patronymic;

    @Column(name = "client_id")
    private Integer clientId;

    @Column(name = "born_date")
    private Date bornDate;

    @Column(unique = true)
    private String email;

    private String town;

    private String street;

    private String house;

    private String flat;

    @Column(unique = true)
    private String login;

    private String password;

    @Column(name = "passport_number")
    private String passportNum;
    @Column(name = "passport_issued_by")
    private String issuedBy;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

}