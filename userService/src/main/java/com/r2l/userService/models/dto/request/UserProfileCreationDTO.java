package com.r2l.userService.models.dto.request;

import java.util.UUID;

public record UserProfileCreationDTO(
		UUID id,
		String name,
		String email
) {
}
