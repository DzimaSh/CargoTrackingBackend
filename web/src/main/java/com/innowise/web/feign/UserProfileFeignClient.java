package com.innowise.web.feign;

import com.innowise.web.dto.user.request.PutUserProfileRequest;
import com.innowise.web.dto.user.response.GetUserProfileResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ConditionalOnProperty(value = "service.user-core-url")
@FeignClient(
        name = "UserProfileFeignClient",
        url = "${service.user-core-url}",
        path = "/api/profile",
        configuration = {CustomFeignErrorDecoder.class}
)
public interface UserProfileFeignClient {

    @GetMapping("/{id}")
    GetUserProfileResponse getProfile(@PathVariable Integer id);

    @PutMapping("/{id}")
    void updateProfile(@RequestBody PutUserProfileRequest request, @PathVariable Integer id);

    @PutMapping("/change-password/{id}")
    void updateUserPassword(@RequestBody String newPassword, @PathVariable Integer id);
}
