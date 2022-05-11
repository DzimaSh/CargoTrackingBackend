package com.innowise.core.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.innowise.core.dto.user.UserRequestDTO;
import com.innowise.core.dto.user.UserResponseDTO;
import com.innowise.core.entity.User;
import com.innowise.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    @GetMapping
    private List<UserResponseDTO> getUsers() {
        return service.getAllUsers().stream().
                map(UserResponseDTO::new).
                collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    private UserResponseDTO getUser(@PathVariable Integer id) {
        return new UserResponseDTO(service.getUserById(id));
    }

    @PostMapping
    private String postUser(@RequestBody UserRequestDTO userRequest){
        User user = userRequest.buildUser();
        System.out.println(user);
        System.out.println(user.getUserRoles().getClass());
        Integer id = service.postUser(userRequest.buildUser());
        return "currentUri/" + id;
    }
}
