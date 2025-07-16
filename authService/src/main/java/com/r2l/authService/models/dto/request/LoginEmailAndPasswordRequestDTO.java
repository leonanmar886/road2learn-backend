package com.r2l.authService.models.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginEmailAndPasswordRequestDTO(
    @NotBlank(message = "Email cannot be empty") @Email(message = "Invalid email") String email,
    @NotBlank(message = "Password cannot be empty") String password) {}
