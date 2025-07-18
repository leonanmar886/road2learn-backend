package com.r2l.userService.models.dto.response;

import com.r2l.userService.models.entity.UserProfile;

public record UserProfileResponseDTO(String username) {
  public static UserProfileResponseDTO fromModel(UserProfile model) {
    return new UserProfileResponseDTO(model.getUsername());
  }
}
