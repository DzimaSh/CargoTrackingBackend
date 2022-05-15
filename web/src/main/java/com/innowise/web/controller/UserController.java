package com.innowise.web.controller;

import com.innowise.core.dto.user.UserRequestDTO;
import com.innowise.core.dto.user.UserResponseDTO;
import com.innowise.web.dto.response.GetAllUsersResponse;
import com.innowise.web.feign.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserFeignClient client;

    @GetMapping
    public GetAllUsersResponse getUsers(@RequestParam(name = "name", required = false) String name,
                                        @RequestParam(name = "surname", required = false) String surname,
                                        @RequestParam(name = "patronymic", required = false) String patronymic,
                                        @RequestParam(name = "beforeBornDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date beforeBornDate,
                                        @RequestParam(name = "afterBornDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date afterBornDate,
                                        @RequestParam(name = "town", required = false) String town,
                                        @RequestParam(name = "street", required = false) String street,
                                        @RequestParam(name = "house", required = false) String house,
                                        @RequestParam(name = "flat", required = false) String flat,
                                        @RequestParam(name = "userRoles", required = false) String[] roles,
                                        @RequestParam(name = "pageSize", required = false) Integer pageSize,
                                        @RequestParam(name = "pageNumber", required = false) Integer pageNumber
    ) {
        Pair<List<UserResponseDTO>, Long> pair = client.
                getUsersByFilterParams(name, surname, patronymic, beforeBornDate, afterBornDate, town, street, house, flat, roles, pageSize, pageNumber);
        return new GetAllUsersResponse(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUser(@PathVariable Integer id) {
        return client.getUserById(id);
    }
    @PostMapping
    public String insertUser(@RequestBody UserRequestDTO userRequest) {
        return client.postUser(userRequest);
    }

    @PostMapping("/date")
    public void date(@RequestParam(name = "beforeBornDate", required = false)
                     @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        System.out.println("123\n\n\n" + date);
        java.sql.Date date1 = new java.sql.Date(date.getTime());
        System.out.println(date1);
        //return "123\n\n\n" + date;
    }
}
