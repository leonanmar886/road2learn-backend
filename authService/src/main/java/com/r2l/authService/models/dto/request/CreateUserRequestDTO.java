package com.r2l.authService.models.dto.request;

import com.r2l.authService.models.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated
public record CreateUserRequestDTO(
    @NotBlank(message = "User name cannot be blank") String name,
    @NotBlank(message = "User email cannot be blank") @Email(message = "Invalid email")
        String email,
    @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password) {
  public User toModel() {
    return new User(name, email);
  }
}
