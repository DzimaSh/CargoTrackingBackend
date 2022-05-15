package com.innowise.core.service.impl;

import com.innowise.core.entity.User;
import com.innowise.core.entity.UserRole;
import com.innowise.core.exceprtion.UserNotFoundException;
import com.innowise.core.repository.UserRepository;
import com.innowise.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceIMPL implements UserService {
    private final EntityManager entityManager;
    private final UserRepository repository;

    @Override
    public User getUserById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException("account not find with id " + id));
    }

    @Override
    public Page<User> getAllUsersByFilterParams(String name, String surname, String patronymic,
                                                Date beforeBornDate, Date afterBornDate, String town,
                                                String street, String house, String flat,
                                                String[] roles, Pageable pageable) {
        EntityType<User> User_ = entityManager.getMetamodel().entity(User.class);
        return repository.findAll(((root, query, builder) -> {
            query.multiselect();
            List<Predicate> predicates = new ArrayList<>();
            if (name != null)
                predicates.add(builder.equal(root.get("name"), name));
            if (surname != null)
                predicates.add(builder.equal(root.get("surname"), surname));
            if (patronymic != null)
                predicates.add(builder.equal(root.get("patronymic"), patronymic));
            if (beforeBornDate != null)
                predicates.add(builder.greaterThan(root.get("bornDate"), new java.sql.Date(beforeBornDate.getTime())));
            if (afterBornDate != null)
                predicates.add(builder.greaterThan(root.get("bornDate"), new java.sql.Date(afterBornDate.getTime())));
            if (town != null)
                predicates.add(builder.equal(root.get("town"), town));
            if (street != null)
                predicates.add(builder.equal(root.get("street"), street));
            if (house != null)
                predicates.add(builder.equal(root.get("house"), house));
            if (flat != null)
                predicates.add(builder.equal(root.get("flat"), flat));
            if (roles != null) {
                SetJoin role = root.join(User_.getSet("userRoles", UserRole.class));
                predicates.add(role.in(Arrays.stream(roles).map(UserRole::valueOf).toArray()));
                query.groupBy(root.get("id")).having(builder.equal(builder.countDistinct(role), roles.length));
            }
            return builder.and(predicates.toArray(new Predicate[]{}));
        }), pageable);
    }

    @Override
    public Integer postUser(User user) {
        return repository.save(user).getId();
    }

    @Override
    public void deleteUsersById(Integer[] ids) {
        try {
            repository.deleteAllById(Arrays.asList(ids));
        } catch (EmptyResultDataAccessException exception) {
            throw new UserNotFoundException(exception.getMessage());
        }
    }

    @Override
    public void updateUser(User updatedUser, Integer id) {
        User userToUpdate = this.getUserById(id);
        updatedUser.setId(userToUpdate.getId());
        repository.save(updatedUser);
    }
}
