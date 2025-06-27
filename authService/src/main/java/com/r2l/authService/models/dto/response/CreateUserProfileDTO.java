package com.r2l.authService.models.dto.response;

import java.util.UUID;

public record CreateUserProfileDTO(
		UUID id,
		String name,
		String email
) {
}
