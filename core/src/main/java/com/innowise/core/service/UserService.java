package com.innowise.core.service;

import com.innowise.core.entity.User;

import java.util.List;

public interface UserService {
    User getUserById(Integer id);
    void postUser(User user);
    List<User> getAllUsers();
}
