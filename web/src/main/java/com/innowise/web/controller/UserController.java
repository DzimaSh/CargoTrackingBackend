package com.innowise.web.controller;

import com.innowise.core.dto.user.UserRequestDTO;
import com.innowise.core.dto.user.UserResponseDTO;
import com.innowise.web.dto.response.GetAllUsersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final RestTemplate restTemplate;

    @GetMapping
    public GetAllUsersResponse getUsers(@RequestParam(name = "name", required = false) String name,
                                        @RequestParam(name = "surname", required = false) String surname,
                                        @RequestParam(name = "patronymic", required = false) String patronymic,
                                        @RequestParam(name = "beforeBornDate", required = false) String beforeBornDate,
                                        @RequestParam(name = "afterBornDate", required = false) String afterBornDate,
                                        @RequestParam(name = "town", required = false) String town,
                                        @RequestParam(name = "street", required = false) String street,
                                        @RequestParam(name = "house", required = false) String house,
                                        @RequestParam(name = "flat", required = false) String flat,
                                        @RequestParam(name = "userRoles", required = false) String[] roles,
                                        @RequestParam(name = "pageSize", required = false) String pageSize,
                                        @RequestParam(name = "pageNumber", required = false) String pageNumber
    ) {
        List<UserResponseDTO> users = restTemplate.getForObject("http://localhost:8081/users", List.class);
        return new GetAllUsersResponse(users);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUser(@PathVariable Integer id) {
        UserResponseDTO userResponse = restTemplate.getForObject("http://localhost:8081/users/{id}", UserResponseDTO.class, id);
        return userResponse;
    }
    @PostMapping
    public String insertUser(@RequestBody UserRequestDTO userRequest) {
        return restTemplate.postForObject("http://localhost:8081/users", userRequest, String.class);
    }

}
