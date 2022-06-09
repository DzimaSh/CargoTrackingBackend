package com.innowise.core.entity.client;

import com.innowise.core.entity.enums.ClientStatus;
import com.innowise.core.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "clients")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ClientStatus status;

    @OneToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private User adminInfo;

    @Column(name = "delete_date")
    private Timestamp deleteDate;

}
