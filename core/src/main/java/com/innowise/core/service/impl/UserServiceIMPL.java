package com.innowise.core.service.impl;

import com.innowise.core.entity.User;
import com.innowise.core.entity.UserRole;
import com.innowise.core.exceprtion.UserNotFoundException;
import com.innowise.core.repository.UserRepository;
import com.innowise.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserServiceIMPL implements UserService {
    private final UserRepository repository;

    @Override
    public User getUserById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException("account not find" + id));
    }
    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public List<User> getUsersByParams(User exampleUser) {
        return repository.findAll(Example.of(exampleUser));
    }

    @Override
    public List<User> getUsersByRoles(String roles) {
        UserRole userRoles = UserRole.valueOf(roles);
        return new ArrayList<>();
    }

    @Override
    public Integer postUser(User user) {
        Integer id = repository.save(user).getId();
        return id;
    }

    public List<User> getUserByDate(Date date) {
        return repository.getAllByBornDateAfter(date);
    }
}
