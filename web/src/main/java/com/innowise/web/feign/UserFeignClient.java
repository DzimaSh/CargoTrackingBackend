package com.innowise.web.feign;

import com.innowise.core.controller.util.GetUsersFilterParams;
import com.innowise.core.dto.user.request.PostUserRequest;
import com.innowise.core.dto.user.request.PutUserRequest;
import com.innowise.core.dto.user.response.GetUsersResponse;
import com.innowise.core.dto.user.response.GetUserByIdResponse;
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
    GetUserByIdResponse getUserById(@PathVariable Integer id);

    @GetMapping
    GetUsersResponse getUsersByFilterParams(@SpringQueryMap(true) GetUsersFilterParams params);

    @PostMapping
    String postUser(@RequestBody PostUserRequest request);

    @DeleteMapping
    void deleteUser(@RequestBody List<Integer> usersIds);

    @PutMapping("/{id}")
    void updateUser(@RequestBody PutUserRequest request, @PathVariable Integer id);
}
