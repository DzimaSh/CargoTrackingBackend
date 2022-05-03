package com.innowise.core.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity(name = "user_table")
@Table
public class User {
    @Id
    @SequenceGenerator(
            name = "id_sequence",
            sequenceName = "id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "id_sequence"
    )
    private Integer id;

    @Size(max = 20, message = "Name is too long")
    private String name;

    @NotEmpty(message = "Surname is required")
    @Size(max = 20, message = "Surname is too long")
    private String surname;

    @Size(max = 20, message = "Patronymic is too long")
    private String patronymic;

    private Integer clientId;

    private Date date;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email isn't valid")
    @Size(max = 50, message = "Email is too long")
    private String email;

    @Size(max = 20, message = "Town name length is too long")
    private String town;

    @Size(max = 20, message = "Street name length is too long")
    private String street;

    @Size(max = 5, message = "House number is too long")
    private String house;

    @Size(max = 5, message = "Flat number is too long")
    private String flat;

    @NotEmpty(message = "Login is required")
    @Size(min = 5, message = "Login is too short")
    @Size(max = 20, message = "Login is too long")
    private String login;

    @NotEmpty(message = "Password is required")
    @Size(min = 5, message = "Password is too short")
    @Size(max = 20, message = "Password is too long")
    private String password;

    @Size(max = 20, message = "Passport number size is too long")
    private String passportNum;

    @Size(max = 20, message = "Establishment that have issued the passport name is too long")
    private String issuedBy;

    @Enumerated(EnumType.STRING)
    @NotEmpty(message = "Role is required")
    private UserRole role;

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getPatronymic() {
        return patronymic;
    }
    public Integer getClientId() {
        return clientId;
    }
    public Date getDate() {
        return date;
    }
    public String getEmail() {
        return email;
    }
    public String getTown() {
        return town;
    }
    public String getStreet() {
        return street;
    }
    public String getHouse() {
        return house;
    }
    public String getFlat() {
        return flat;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
    public String getPassportNum() {
        return passportNum;
    }
    public String getIssuedBy() {
        return issuedBy;
    }
    public UserRole getRole() {
        return role;
    }
}
