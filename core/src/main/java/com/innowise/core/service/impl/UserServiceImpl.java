package com.innowise.core.service.impl;

import com.innowise.core.controller.util.GetUsersFilterParams;
import com.innowise.core.dto.user.response.GetUserResponse;
import com.innowise.core.dto.user.response.GetUsersResponse;
import com.innowise.core.entity.role.Role;
import com.innowise.core.entity.role.Role_;
import com.innowise.core.entity.user.User;
import com.innowise.core.entity.enums.Roles;
import com.innowise.core.entity.user.User_;
import com.innowise.core.exceprtion.UserExistsException;
import com.innowise.core.exceprtion.UserNotFoundException;
import com.innowise.core.repository.RoleRepository;
import com.innowise.core.repository.UserRepository;
import com.innowise.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public GetUserResponse getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        return new GetUserResponse(user);
    }

    @Override
    public GetUsersResponse getAllUsersByFilterParams(GetUsersFilterParams params) {
        Page<User> page = userRepository.findAll(((root, query, builder) ->
                        filteringUsersToPredicate(root, query, builder, params)
                )
                , PageRequest.of(params.getPageNumber(), params.getPageSize()));
        List<GetUserResponse> users = page.getContent().stream().
                map(GetUserResponse::new).
                collect(Collectors.toList());
        return new GetUsersResponse(users, page.getTotalElements());
    }

    @Override
    public GetUserResponse getUserByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("User with login " + login + " not found"));
        return new GetUserResponse(user);
    }

    @Override
    public Integer createUser(User user) {
        if (isUserSys_Admin(user) && isSys_AdminExists())
            throw new UserExistsException("Sys_admin already exists");
        return userRepository.save(user).getId();
    }

    @Override
    public void deleteUsersById(List<Integer> ids) {
        ids.forEach(id -> {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not find with id " + id));

            if (isUserSys_Admin(user) && isSys_AdminExists())
                throw new UserExistsException("You can't delete sys_admin");

            userRepository.delete(user);
        });
    }

    @Override
    public void updateUser(User updatedUser, Integer id) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        if (!isUserSys_Admin(userToUpdate)) {
            if (isUserSys_Admin(updatedUser) && isSys_AdminExists())
                throw new UserExistsException("Sys_admin already exists");
        } else {
            throw new UserExistsException("You can't update sys_admin");
        }

        updatedUser.setId(userToUpdate.getId());
        userRepository.save(updatedUser);
    }


    private Predicate filteringUsersToPredicate(Root<User> root, CriteriaQuery query, CriteriaBuilder builder, GetUsersFilterParams params) {
        EntityType<User> user = entityManager.getMetamodel().entity(User.class);
        query.multiselect();
        List<Predicate> predicates = new ArrayList<>();
        if (params.getName() != null)
            predicates.add(builder.equal(root.get(User_.name), params.getName()));
        if (params.getSurname() != null)
            predicates.add(builder.equal(root.get(User_.surname), params.getSurname()));
        if (params.getPatronymic() != null)
            predicates.add(builder.equal(root.get(User_.patronymic), params.getPatronymic()));
        if (params.getBeforeBornDate() != null)
            predicates.add(builder.lessThan(root.get(User_.bornDate), new java.sql.Date(params.getBeforeBornDate().getTime())));
        if (params.getAfterBornDate() != null)
            predicates.add(builder.greaterThan(root.get(User_.bornDate), new java.sql.Date(params.getAfterBornDate().getTime())));
        if (params.getTown() != null)
            predicates.add(builder.equal(root.get(User_.town), params.getTown()));
        if (params.getStreet() != null)
            predicates.add(builder.equal(root.get(User_.street), params.getStreet()));
        if (params.getHouse() != null)
            predicates.add(builder.equal(root.get(User_.street), params.getHouse()));
        if (params.getFlat() != null)
            predicates.add(builder.equal(root.get(User_.flat), params.getFlat()));
        if (params.getRoles() != null) {
            SetJoin role = root.join(user.getSet(User_.ROLES, Role.class));
            predicates.add(role.get(Role_.role).in(Arrays.stream(params.getRoles()).map(Roles::valueOf).toArray()));
            query.groupBy(root.get("id")).having(builder.equal(builder.countDistinct(role), params.getRoles().length));
        }
        return builder.and(predicates.toArray(new Predicate[]{}));
    }

    private boolean isUserSys_Admin(User user) {
        for (Role role : user.getRoles()) {
            if (role.getRole().equals(Roles.SYS_ADMIN))
                return true;
        }
        return false;
    }

    private boolean isSys_AdminExists() {
        return roleRepository.findById(Roles.SYS_ADMIN.ordinal()).isPresent();
    }
}
