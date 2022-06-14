package com.innowise.core.entity.client;

import com.innowise.core.entity.enums.ClientSubjectStatus;
import com.innowise.core.entity.user.User;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity(name = "clients")
@TypeDef(name = "enum_type", typeClass = PostgreSQLEnumType.class)
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
    @Type(type = "enum_type")
    private ClientSubjectStatus subjectStatus;

    @OneToOne(mappedBy = "client", fetch = FetchType.LAZY)
    private User adminInfo;

    @Column(name = "delete_date")
    private Timestamp deleteDate;

    //@PreRemove
    public void nullifyClientInUser() {
        adminInfo.setClient(null);
    }
}
