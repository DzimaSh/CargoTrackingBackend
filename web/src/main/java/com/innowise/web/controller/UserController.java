package com.innowise.web.controller;

import com.innowise.core.dto.user.UserRequestDTO;
import com.innowise.core.dto.user.UserResponseDTO;
import com.innowise.web.dto.request.UpdateUserRequest;
import com.innowise.web.dto.response.GetAllUsersResponse;
import com.innowise.web.exception.ValidationException;
import com.innowise.web.feign.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserFeignClient client;

    @GetMapping
    public ResponseEntity<GetAllUsersResponse> getUsers(@RequestParam(name = "name", required = false) String name,
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
        return new ResponseEntity<>(new GetAllUsersResponse(pair.getFirst(), pair.getSecond()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Integer id) {
        return new ResponseEntity<>(client.getUserById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> insertUser(@RequestBody @Valid UserRequestDTO userRequest, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationException(result);
        return new ResponseEntity<>(client.postUser(userRequest), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody Integer[] ids) {
        client.deleteUser(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UpdateUserRequest request, BindingResult result,
                                           @PathVariable Integer id) {
        if (result.hasErrors())
            throw new ValidationException(result);
        client.updateUser(request, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
