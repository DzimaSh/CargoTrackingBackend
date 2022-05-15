package com.innowise.core.controller;

import com.innowise.core.dto.user.UserRequestDTO;
import com.innowise.core.dto.user.UserResponseDTO;
import com.innowise.core.entity.User;
import com.innowise.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    @GetMapping
    private Pair<List<UserResponseDTO>, Long> getUsers(@RequestParam(name = "name", required = false) String name,
                                           @RequestParam(name = "surname", required = false) String surname,
                                           @RequestParam(name = "patronymic", required = false) String patronymic,
                                           @RequestParam(name = "beforeBornDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date beforeBornDate,
                                           @RequestParam(name = "afterBornDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date afterBornDate,
                                           @RequestParam(name = "town", required = false) String town,
                                           @RequestParam(name = "street", required = false) String street,
                                           @RequestParam(name = "house", required = false) String house,
                                           @RequestParam(name = "flat", required = false) String flat,
                                           @RequestParam(name = "userRoles", required = false) String[] roles,
                                           @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                                           @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber) {
        Page<User> page = service.getAllUsersByFilterParams(name, surname, patronymic,
                beforeBornDate, afterBornDate, town,
                street, house, flat,
                roles, PageRequest.of(pageNumber, pageSize));
        List<UserResponseDTO> users = page.getContent().stream().
                map(UserResponseDTO::new).
                collect(Collectors.toList());
        Pair<List<UserResponseDTO>, Long> pair = Pair.of(users, page.getTotalElements());
        return pair;
    }

    @GetMapping("/{id}")
    private UserResponseDTO getUser(@PathVariable Integer id) {
        return new UserResponseDTO(service.getUserById(id));
    }

    @PostMapping
    private String postUser(@RequestBody UserRequestDTO userRequest){
        User user = userRequest.buildUser();
        Integer id = service.postUser(user);
        return "currentUri/" + id;
    }

    @DeleteMapping
    private void deleteUser(@RequestBody Integer[] ids) {
        service.deleteUsersById(ids);
    }

    @PutMapping("/{id}")
    private void updateUser(@RequestBody UserRequestDTO request, @PathVariable Integer id) {
        service.updateUser(request.buildUser(), id);
    }
}
