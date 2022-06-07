package com.innowise.web.controller;

import com.innowise.web.controller.util.GetUsersFilterParams;
import com.innowise.web.dto.user.request.PostUserRequest;
import com.innowise.web.dto.user.request.PutUserRequest;
import com.innowise.web.dto.user.response.GetUserResponse;
import com.innowise.web.dto.user.response.GetUsersResponse;
import com.innowise.web.exception.ArgumentsNotValidException;
import com.innowise.web.exception.ValidationException;
import com.innowise.web.feign.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserFeignClient usersServiceClient;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<GetUsersResponse> getUsers(GetUsersFilterParams params) {
        GetUsersResponse response = usersServiceClient.getUsersByFilterParams(params);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable Integer id) {
        return new ResponseEntity<>(usersServiceClient.getUserById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> insertUser(@RequestBody @Valid PostUserRequest userRequest, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationException(result);
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return new ResponseEntity<>(usersServiceClient.postUser(userRequest), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody List<Integer> userIds) {
        if (userIds.contains(null))
            throw new ArgumentsNotValidException("Given list contains empty values");
        usersServiceClient.deleteUser(userIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@RequestBody @Valid PutUserRequest userRequest, BindingResult result,
                                           @PathVariable Integer id) {
        if (result.hasErrors())
            throw new ValidationException(result);

        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        usersServiceClient.updateUser(userRequest, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
