package com.innowise.core.entity.role;

import com.innowise.core.entity.enums.Roles;
import com.innowise.core.entity.user.User;
import org.hibernate.metamodel.model.domain.internal.SetAttributeImpl;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Set;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public class Role_ {
    public static volatile SingularAttribute<Role, String> id;
    public static volatile SingularAttribute<Role, Roles> role;
    public static volatile SetAttributeImpl<Role, Set<User>> users;

    public static final String ID = "id";
    public static final String ROLE = "role";
    public static final String USERS = "users";
}
