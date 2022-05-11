package com.innowise.core.service;

import com.innowise.core.entity.User;
import com.innowise.core.entity.UserRole;

import java.sql.Date;
import java.util.List;

public interface UserService {
    User getUserById(Integer id);

    Integer postUser(User user);
    List<User> getAllUsers();

    List<User> getUsersByParams(User exampleUser);

    List<User> getUsersByRoles(String role);

    List<User> getUserByDate(Date date);
}
