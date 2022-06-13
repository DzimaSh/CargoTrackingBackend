package com.innowise.core.controller;

import com.innowise.core.dto.user.request.PutUserProfileRequest;
import com.innowise.core.dto.user.response.GetUserProfileResponse;
import com.innowise.core.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService profileService;

    @GetMapping("/{id}")
    private GetUserProfileResponse getUserProfile(@PathVariable Integer id) {
        return profileService.getUserProfile(id);
    }

    @PutMapping("/{id}")
    private void updateUserProfile(@RequestBody PutUserProfileRequest request, @PathVariable Integer id) {
        profileService.updateUserProfile(request, id);
    }

    @PutMapping("/change-password/{id}")
    private void changeUserPassword(@RequestBody String newPassword, @PathVariable Integer id) {
        profileService.changeUserPassword(id, newPassword);
    }
}
