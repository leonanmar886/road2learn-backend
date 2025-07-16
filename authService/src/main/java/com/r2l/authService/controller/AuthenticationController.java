package com.r2l.authService.controller;

import com.r2l.authService.models.dto.request.CreateUserRequestDTO;
import com.r2l.authService.models.dto.request.LoginEmailAndPasswordRequestDTO;
import com.r2l.authService.models.dto.response.LoginEmailAndPasswordResponseDTO;
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

  @PostMapping("/register")
  public ResponseEntity<String> createUser(@RequestBody @Valid CreateUserRequestDTO body) {
    authenticationService.createUser(body);
    return ResponseEntity.ok("Success");
  }

  @PostMapping("/login")
  public ResponseEntity<LoginEmailAndPasswordResponseDTO> loginWithEmailAndPassword(
      @RequestBody @Valid LoginEmailAndPasswordRequestDTO body) {
    LoginEmailAndPasswordResponseDTO response =
        authenticationService.loginWithEmailAndPassword(body);
    return ResponseEntity.ok(response);
  }
}
