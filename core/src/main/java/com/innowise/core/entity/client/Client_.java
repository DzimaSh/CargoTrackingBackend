package com.innowise.core.entity.client;

import com.innowise.core.entity.enums.ClientSubjectStatus;
import com.innowise.core.entity.user.User;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Client.class)
public class Client_ {
    public static volatile SingularAttribute<Client, Integer> id;
    public static volatile SingularAttribute<Client, String> name;
    public static volatile SingularAttribute<Client, ClientSubjectStatus> subjectStatus;
    public static volatile SingularAttribute<Client, User> adminInfo;
    public static volatile SingularAttribute<Client, Date> deleteDate;
    public static volatile SetAttribute<Client, ClientActivity> clientActivity;

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SUBJECT_STATUS = "subjectStatus";
    public static final String ADMIN_INFO = "adminInfo";
    public static final String DELETE_DATE = "deleteDate";
    public static final String CLIENT_ACTIVITY = "clientActivity";
}
