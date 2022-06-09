package com.innowise.core.entity.user;

import com.innowise.core.entity.role.Role;
import org.hibernate.metamodel.model.domain.internal.SetAttributeImpl;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;
import java.util.Set;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public class User_ {
    public static volatile SingularAttribute<User, Integer> id;
    public static volatile SingularAttribute<User, String> name;
    public static volatile SingularAttribute<User, String> surname;
    public static volatile SingularAttribute<User, String> patronymic;
    public static volatile SingularAttribute<User, Integer> clientId;
    public static volatile SingularAttribute<User, Date> bornDate;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, String> town;
    public static volatile SingularAttribute<User, String> street;
    public static volatile SingularAttribute<User, String> house;
    public static volatile SingularAttribute<User, String> flat;
    public static volatile SingularAttribute<User, String> login;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, String> passportNum;
    public static volatile SingularAttribute<User, String> issuedBy;
    public static volatile SetAttributeImpl<User, Set<Role>> roles;

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String PATRONYMIC = "patronymic";
    public static final String CLIENT_ID = "clientId";
    public static final String BORN_DATE = "bornDate";
    public static final String EMAIL = "email";
    public static final String TOWN = "town";
    public static final String STREET = "street";
    public static final String HOUSE = "house";
    public static final String FLAT = "flat";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String PASSPORT_NUM = "passportNum";
    public static final String ISSUED_BY = "issuedBy";
    public static final String ROLES = "roles";

}