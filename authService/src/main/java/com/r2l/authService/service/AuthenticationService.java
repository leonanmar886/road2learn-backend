package com.r2l.authService.service;

import com.r2l.authService.exception.CreateUserProfileException;
import com.r2l.authService.exception.UserAlreadyExists;
import com.r2l.authService.exception.UserLoginWithCredentialsException;
import com.r2l.authService.models.dto.request.CreateUserRequestDTO;
import com.r2l.authService.models.dto.request.LoginEmailAndPasswordRequestDTO;
import com.r2l.authService.models.dto.response.CreateUserProfileDTO;
import com.r2l.authService.models.dto.response.LoginEmailAndPasswordResponseDTO;
import com.r2l.authService.models.entity.User;
import com.r2l.authService.repository.UserRepository;
import com.r2l.authService.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository userRepository;

  private final CreateUserProducer createUserProducer;

  private final PasswordEncoder passwordEncoder;

  private final CustomUserDetailsService userDetailsService;

  private final JwtUtil jwtUtil;

  private final AuthenticationManager authenticationManager;

  @Transactional
  public void createUser(CreateUserRequestDTO request) {
    User newUser = request.toModel();

    if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
      throw new UserAlreadyExists("A user with this email already was registered.");
    }

    newUser.setPassword(passwordEncoder.encode(request.password()));

    try {
      newUser = userRepository.save(newUser);
      createUserProducer.send(CreateUserProfileDTO.fromModel(newUser));
    } catch (Exception e) {
      userRepository.delete(newUser);
      throw new CreateUserProfileException("Error creating user profile: " + e.getMessage());
    }
  }

  public LoginEmailAndPasswordResponseDTO loginWithEmailAndPassword(LoginEmailAndPasswordRequestDTO dto) {
    Authentication auth = null;

    try {
      auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
    } catch (AuthenticationException ex) {
      throw new UserLoginWithCredentialsException("Invalid email or password");
    }

    UserDetails userDetails = (UserDetails) auth.getPrincipal();

    String token = jwtUtil.generateToken(userDetails);

    return new LoginEmailAndPasswordResponseDTO(token);
  }
}
