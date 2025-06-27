package com.r2l.authService.controller;

import com.r2l.authService.models.dto.response.CreateUserProfileDTO;
import com.r2l.authService.service.CreateUserProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final CreateUserProducer createUserProducer;

	@GetMapping("/create")
	public ResponseEntity<String> createUser() {
		createUserProducer.send(new CreateUserProfileDTO(UUID.randomUUID(), "John Doe", "leonan@gmail.com"));
		return ResponseEntity.ok("Success");
	}
}

