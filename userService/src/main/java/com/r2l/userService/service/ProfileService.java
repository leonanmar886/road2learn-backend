package com.r2l.userService.service;

import com.r2l.userService.models.dto.response.UserProfileResponseDTO;
import com.r2l.userService.models.entity.UserProfile;
import com.r2l.userService.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

  private final UserProfileRepository userProfileRepository;

  public UserProfileResponseDTO getUserProfile(String userId) {
    UserProfile userProfile =
        userProfileRepository
            .findByUserId((UUID.fromString(userId)))
            .orElseThrow(() -> new UsernameNotFoundException("User profile not found"));

    return UserProfileResponseDTO.fromModel(userProfile);
  }
}
