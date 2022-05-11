package com.innowise.core.entity;

import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;

@Entity(name = "users")
@TypeDefs({
        @TypeDef(
                name = "enum-array",
                typeClass = EnumArrayType.class
        )
})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 20, message = "Name is too long")
    private String name;

    @NotEmpty(message = "Surname is required")
    @Size(max = 20, message = "Surname is too long")
    private String surname;

    @Size(max = 20, message = "Patronymic is too long")
    private String patronymic;

    @Column(name = "client_id")
    private Integer clientId;

    @Column(name = "born_date")
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
    @Column(name = "passport_number")
    private String passportNum;

    @Size(max = 50, message = "Establishment that have issued the passport name is too long")
    @Column(name = "passport_issued_by")
    private String issuedBy;

    @ElementCollection(targetClass = UserRole.class)
    @CollectionTable(name="user_roles_table")
    @Enumerated(EnumType.STRING)
    @Column(name = "userRoles")
    private List<UserRole> userRoles;

}