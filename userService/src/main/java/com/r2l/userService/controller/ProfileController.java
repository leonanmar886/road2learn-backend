package com.r2l.userService.controller;

import com.r2l.userService.models.dto.response.UserProfileResponseDTO;
import com.r2l.userService.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

  private final ProfileService profileService;

  @GetMapping("/me")
  public ResponseEntity<UserProfileResponseDTO> getUserProfile(
      @RequestHeader("X-User-Id") String userIdString) {
    UserProfileResponseDTO userProfile = profileService.getUserProfile(userIdString);

    return ResponseEntity.ok(userProfile);
  }
}
