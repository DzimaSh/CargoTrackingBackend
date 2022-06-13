package com.innowise.core.service;

import com.innowise.core.dto.user.request.PutUserProfileRequest;
import com.innowise.core.dto.user.response.GetUserProfileResponse;

public interface UserProfileService {
    GetUserProfileResponse getUserProfile(Integer id);
    void updateUserProfile(PutUserProfileRequest userProfileRequest, Integer id);
    void changeUserPassword(Integer id, String newPassword);
}
