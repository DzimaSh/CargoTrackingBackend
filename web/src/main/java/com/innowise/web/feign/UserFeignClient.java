package com.innowise.web.feign;

import com.innowise.web.controller.util.GetUsersFilterParams;
import com.innowise.web.dto.user.request.GetUserByLoginRequest;
import com.innowise.web.dto.user.request.PostUserRequest;
import com.innowise.web.dto.user.request.PutUserRequest;
import com.innowise.web.dto.user.response.GetUserResponse;
import com.innowise.web.dto.user.response.GetUsersResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ConditionalOnProperty(value = "service.user-core-url")
@FeignClient(
        name = "UserFeignClient",
        url = "${service.user-core-url}",
        path = "/api/users"
)
public interface UserFeignClient {
    @GetMapping(value = "/{id}")
    GetUserResponse getUserById(@PathVariable Integer id);

    @GetMapping
    GetUsersResponse getUsersByFilterParams(@SpringQueryMap(true) GetUsersFilterParams params);

    @PutMapping
    GetUserResponse getUserByLogin(@RequestBody GetUserByLoginRequest request);

    @PostMapping
    String postUser(@RequestBody PostUserRequest request);

    @DeleteMapping
    void deleteUser(@RequestBody List<Integer> usersIds);

    @PutMapping("/{id}")
    void updateUser(@RequestBody PutUserRequest request, @PathVariable Integer id);
}
