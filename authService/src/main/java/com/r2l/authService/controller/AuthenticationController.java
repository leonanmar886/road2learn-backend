package com.r2l.authService.controller;

import com.r2l.authService.models.dto.request.CreateUserRequestDTO;
import com.r2l.authService.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	@PostMapping()
	public ResponseEntity<String> createUser(@RequestBody @Valid CreateUserRequestDTO body) {
		authenticationService.createUser(body);
		return ResponseEntity.ok("Success");
	}
}

