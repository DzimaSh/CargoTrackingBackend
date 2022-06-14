package com.innowise.core.entity.role;

import com.innowise.core.entity.enums.Roles;
import com.innowise.core.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "role")
public class Role {
    @Id
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role(Integer id, Roles role) {
        this.id = id;
        this.role = role;
    }
}
