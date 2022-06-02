package com.innowise.core.service;

import com.innowise.core.controller.util.GetUsersFilterParams;
import com.innowise.core.dto.user.response.GetUserResponse;
import com.innowise.core.dto.user.response.GetUsersResponse;
import com.innowise.core.entity.user.User;

import java.util.List;

public interface UserService {
    GetUserResponse getUserById(Integer id);
    Integer createUser(User user);
    GetUsersResponse getAllUsersByFilterParams(GetUsersFilterParams params);
    GetUserResponse getUserByLogin(String login);
    void deleteUsersById(List<Integer> ids);
    void updateUser(User updatedUser, Integer id);
}
