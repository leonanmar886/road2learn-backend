package com.r2l.authService.models.dto.response;

import com.r2l.authService.models.entity.User;
import java.util.UUID;

public record CreateUserProfileDTO(UUID id, String name, String email) {
  @Override
  public String toString() {
    return "CreateUserProfileDTO{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", email='"
        + email
        + '\''
        + '}';
  }

  public static CreateUserProfileDTO fromModel(User user) {
    return new CreateUserProfileDTO(user.getId(), user.getName(), user.getEmail());
  }
}
