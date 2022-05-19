package com.innowise.core.service;

import com.innowise.core.controller.util.GetUsersFilterParams;
import com.innowise.core.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    User getUserById(Integer id);
    Integer createUser(User user);
    Page<User> getAllUsersByFilterParams(GetUsersFilterParams params);
    void deleteUsersById(List<Integer> ids);
    void updateUser(User updatedUser, Integer id);
}
