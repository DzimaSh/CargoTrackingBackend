package com.innowise.core.controller;

import com.innowise.core.controller.util.GetUsersFilterParams;
import com.innowise.core.dto.user.response.GetUsersResponse;
import com.innowise.core.dto.user.request.PostUserRequest;
import com.innowise.core.dto.user.response.GetUserByIdResponse;
import com.innowise.core.entity.User;
import com.innowise.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping
    private GetUsersResponse getUsers(GetUsersFilterParams params) {
        Page<User> page = userService.getAllUsersByFilterParams(params);
        List<GetUserByIdResponse> users = page.getContent().stream().
                map(GetUserByIdResponse::new).
                collect(Collectors.toList());
        return new GetUsersResponse(users, page.getTotalElements());
    }

    @GetMapping("/{id}")
    private GetUserByIdResponse getUser(@PathVariable Integer id) {
        return new GetUserByIdResponse(userService.getUserById(id));
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
