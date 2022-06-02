package com.innowise.core.controller;

import com.innowise.core.controller.util.GetUsersFilterParams;
import com.innowise.core.dto.user.request.GetUserByLoginRequest;
import com.innowise.core.dto.user.response.GetUsersResponse;
import com.innowise.core.dto.user.request.PostUserRequest;
import com.innowise.core.dto.user.response.GetUserByIdResponse;
import com.innowise.core.entity.user.User;
import com.innowise.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    private GetUserByIdResponse getUser(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping
    private User getUserByLogin(@RequestBody GetUserByLoginRequest request) {
        return userService.getUserByLogin(request.getLogin());
    }

    @PostMapping
    private String postUser(@RequestBody PostUserRequest userRequest){
        User user = userRequest.buildUser();
        Integer id = userService.createUser(user);
        return "currentUri/" + id;
    }

    @DeleteMapping
    private void deleteUser(@RequestBody List<Integer> usersIds) {
        userService.deleteUsersById(usersIds);
    }

    @PutMapping("/{id}")
    private void updateUser(@RequestBody PostUserRequest request, @PathVariable Integer id) {
        userService.updateUser(request.buildUser(), id);
    }
}
