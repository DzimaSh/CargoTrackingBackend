package com.innowise.core.service;

import com.innowise.core.entity.User;
import com.innowise.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    @GetMapping("/{id}")
    private User getUser(@PathVariable Integer id) {
        return service.getUserById(id);
    }
}
