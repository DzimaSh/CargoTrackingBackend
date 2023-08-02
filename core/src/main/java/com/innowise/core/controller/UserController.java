package com.innowise.core.controller;

import com.innowise.core.controller.util.GetUsersFilterParams;
import com.innowise.core.dto.user.request.GetUserByLoginRequest;
import com.innowise.core.dto.user.request.PutUserRequest;
import com.innowise.core.dto.user.response.GetUsersResponse;
import com.innowise.core.dto.user.request.PostUserRequest;
import com.innowise.core.dto.user.response.GetUserResponse;
import com.innowise.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    private GetUsersResponse getUsers(GetUsersFilterParams params) {
        return userService.getAllUsersByFilterParams(params);
    }

    @GetMapping("/{id}")
    private GetUserResponse getUser(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping
    private GetUserResponse getUserByLogin(@RequestBody GetUserByLoginRequest request) {
        return userService.getUserByLogin(request.getLogin());
    }

    @PostMapping
    private Integer postUser(@RequestBody @Valid PostUserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @DeleteMapping
    private void deleteUser(@RequestBody List<Integer> usersIds) {
        userService.deleteUsersById(usersIds);
    }

    @PutMapping("/{id}")
    private void updateUser(@RequestBody PutUserRequest userRequest, @PathVariable Integer id) {
        userService.updateUser(userRequest, id);
    }
}
