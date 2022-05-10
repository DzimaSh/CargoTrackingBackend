package com.innowise.web.controller;

import com.innowise.core.entity.User;
import com.innowise.web.dto.response.GetUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final RestTemplate restTemplate;

    @GetMapping("/{id}")
    public GetUserResponse getUser(@PathVariable Integer id) {
        User user = restTemplate.getForObject("http://localhost:8081/users/{id}", User.class, id);
        return new GetUserResponse(user);
    }

}
