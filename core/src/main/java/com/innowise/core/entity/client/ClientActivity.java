package com.innowise.core.entity.client;

import com.innowise.core.entity.enums.ClientActivationStatus;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "clients_activity_status_history")
@TypeDef(name = "enum-type", typeClass = PostgreSQLEnumType.class)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Type(type = "enum-type")
    @Column(name = "status")
    private ClientActivationStatus activationStatus;

    private Timestamp date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

}
