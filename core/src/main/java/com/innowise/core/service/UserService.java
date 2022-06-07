package com.innowise.core.service;

import com.innowise.core.controller.util.GetUsersFilterParams;
import com.innowise.core.dto.user.request.PostUserRequest;
import com.innowise.core.dto.user.request.PutUserRequest;
import com.innowise.core.dto.user.response.GetUserResponse;
import com.innowise.core.dto.user.response.GetUsersResponse;

import java.util.List;

public interface UserService {
    GetUserResponse getUserById(Integer id);
    Integer createUser(PostUserRequest userRequest);
    GetUsersResponse getAllUsersByFilterParams(GetUsersFilterParams params);
    GetUserResponse getUserByLogin(String login);
    void deleteUsersById(List<Integer> ids);
    void updateUser(PutUserRequest userRequest, Integer id);
}
