package com.innowise.core.service.impl;

import com.innowise.core.dto.user.request.PutUserProfileRequest;
import com.innowise.core.dto.user.response.GetUserProfileResponse;
import com.innowise.core.entity.user.User;
import com.innowise.core.exceprtion.UserNotFoundException;
import com.innowise.core.repository.UserRepository;
import com.innowise.core.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserRepository userRepository;

    @Override
    public GetUserProfileResponse getUserProfile(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found", HttpStatus.NOT_FOUND));
        return new GetUserProfileResponse(user);
    }

    @Override
    public void updateUserProfile(PutUserProfileRequest userProfileRequest, Integer id) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found", HttpStatus.NOT_FOUND));
        userProfileRequest.updateUserFromRequest(userToUpdate);
        userRepository.save(userToUpdate);
    }

    @Override
    public void changeUserPassword(Integer id, String newPassword) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found", HttpStatus.NOT_FOUND));
        userToUpdate.setPassword(newPassword);
        userRepository.save(userToUpdate);
    }
}
