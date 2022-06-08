package com.innowise.web.controller;

import com.innowise.web.dto.user.request.ChangePasswordRequest;
import com.innowise.web.dto.user.request.PutUserProfileRequest;
import com.innowise.web.dto.user.response.GetUserProfileResponse;
import com.innowise.web.exception.ArgumentsNotValidException;
import com.innowise.web.exception.ValidationException;
import com.innowise.web.feign.UserProfileFeignClient;
import com.innowise.web.security.jwt.JwtUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileFeignClient profileFeignClient;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<GetUserProfileResponse> getUserProfile(@AuthenticationPrincipal JwtUser user) {
        GetUserProfileResponse response = profileFeignClient.getProfile(user.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> updateUserProfile(@AuthenticationPrincipal JwtUser user,
                                                  @RequestBody @Valid PutUserProfileRequest request,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult);

        profileFeignClient.updateProfile(request, user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/change-password")
    public ResponseEntity<Void> changeUserPassword(@AuthenticationPrincipal JwtUser user,
                                                   @RequestBody @Valid ChangePasswordRequest request,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult);

        if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            String newPassword = passwordEncoder.encode(request.getNewPassword());
            profileFeignClient.updateUserPassword(newPassword, user.getId());
        }
        else {
            throw new ArgumentsNotValidException("The old password you entered is incorrect");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
