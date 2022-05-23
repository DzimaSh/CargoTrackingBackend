package com.innowise.core.entity.role;

import com.innowise.core.entity.enums.Roles;
import lombok.*;

import javax.persistence.*;

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
}
