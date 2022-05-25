package com.innowise.core.service;

import com.innowise.core.controller.util.GetUsersFilterParams;
import com.innowise.core.dto.user.response.GetUserByIdResponse;
import com.innowise.core.dto.user.response.GetUsersResponse;
import com.innowise.core.entity.user.User;

import java.util.List;

public interface UserService {
    GetUserByIdResponse getUserById(Integer id);
    Integer createUser(User user);
    GetUsersResponse getAllUsersByFilterParams(GetUsersFilterParams params);
    User getUserByLogin(String login);
    void deleteUsersById(List<Integer> ids);
    void updateUser(User updatedUser, Integer id);
}
