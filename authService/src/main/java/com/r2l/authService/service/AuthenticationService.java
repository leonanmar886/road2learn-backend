package com.r2l.authService.service;

import com.r2l.authService.exception.CreateUserProfileException;
import com.r2l.authService.exception.UserAlreadyExists;
import com.r2l.authService.models.dto.request.CreateUserRequestDTO;
import com.r2l.authService.models.dto.response.CreateUserProfileDTO;
import com.r2l.authService.models.entity.User;
import com.r2l.authService.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository userRepository;

	private final CreateUserProducer createUserProducer;

	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void createUser(CreateUserRequestDTO request){
		User newUser = request.toModel();

		if (userRepository.findByEmail(newUser.getEmail()).isPresent()){
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
}
