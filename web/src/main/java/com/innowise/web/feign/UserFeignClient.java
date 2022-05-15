package com.innowise.web.feign;

import com.innowise.core.dto.user.UserRequestDTO;
import com.innowise.core.dto.user.UserResponseDTO;
import com.innowise.web.dto.request.UpdateUserRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.util.Pair;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@ConditionalOnProperty(value = "service.user-core-url")
@FeignClient(
        name = "UserFeignClient",
        url = "${service.user-core-url}",
        path = "/users"
)
public interface UserFeignClient {
    @GetMapping(value = "/{id}")
    UserResponseDTO getUserById(@PathVariable Integer id);

    @GetMapping
    Pair<List<UserResponseDTO>, Long> getUsersByFilterParams(@RequestParam(name = "name", required = false) String name,
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
                                                                @RequestParam(name = "pageNumber", required = false) Integer pageNumber);

    @PostMapping
    String postUser(@RequestBody UserRequestDTO request);

    @DeleteMapping
    void deleteUser(@RequestBody Integer[] ids);

    @PutMapping("/{id}")
    void updateUser(@RequestBody UpdateUserRequest request, @PathVariable Integer id);
}
