package com.innowise.core.dto.user.request;

import com.innowise.core.entity.user.User;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PutUserProfileRequest {
    @Size(max = 20, message = "Name is too long")
    private String name;

    @NotEmpty(message = "Surname is required")
    @Size(max = 20, message = "Surname is too long")
    private String surname;

    @Size(max = 20, message = "Patronymic is too long")
    private String patronymic;

    private Date bornDate;

    @Size(max = 20, message = "Town name length is too long")
    private String town;

    @Size(max = 20, message = "Street name length is too long")
    private String street;

    @Size(max = 5, message = "Flat number is too long")
    private String flat;

    public void updateUserFromRequest(User userToUpdate) {
        userToUpdate.setName(this.getName());
        userToUpdate.setSurname(this.getSurname());
        userToUpdate.setPatronymic(this.getPatronymic());
        userToUpdate.setBornDate(this.getBornDate());
        userToUpdate.setTown(this.getTown());
        userToUpdate.setStreet(this.getStreet());
        userToUpdate.setFlat(this.getFlat());
    }
}
