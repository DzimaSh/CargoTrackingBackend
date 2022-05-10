package com.innowise.core.entity;

import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

@Entity(name = "users")
@TypeDefs({
        @TypeDef(
                name = "enum-array",
                typeClass = EnumArrayType.class
        )
})
@Getter
@NoArgsConstructor
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

    private Date bornDate;

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
    @Size(max = 15, message = "Login is too long")
    private String login;

    @NotEmpty(message = "Password is required")
    @Size(min = 5, message = "Password is too short")
    @Size(max = 72, message = "Password is too long")
    private String password;

    @Size(max = 30, message = "Passport number size is too long")
    private String passportNumber;

    @Size(max = 50, message = "Establishment that have issued the passport name is too long")
    private String passportIssuedBy;

    @Type(type = "enum-array")
    @Enumerated(EnumType.STRING)
    private UserRole[] roles;

    public User(String name,
                String surname,
                String patronymic,
                Integer clientId,
                Date bornDate,
                String email,
                String town,
                String street,
                String house,
                String flat,
                String login,
                String password,
                String passportNumber,
                String passportIssuedBy,
                UserRole[] roles) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.clientId = clientId;
        this.bornDate = bornDate;
        this.email = email;
        this.town = town;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.login = login;
        this.password = password;
        this.passportNumber = passportNumber;
        this.passportIssuedBy = passportIssuedBy;
        this.roles = roles;
    }
}