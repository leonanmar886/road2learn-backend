package com.r2l.authService.service;

import com.r2l.authService.exception.UserAlreadyExists;
import com.r2l.authService.models.dto.request.CreateUserRequestDTO;
import com.r2l.authService.models.entity.User;
import com.r2l.authService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository userRepository;

	private final CreateUserProducer createUserProducer;

	public User createUser(CreateUserRequestDTO request){
		User newUser = request.toModel();

		if (userRepository.findByEmail(newUser.getEmail()).isPresent()){
			throw new UserAlreadyExists("A user with this email already was registered.");
		}

		return userRepository.save(newUser);
	}
}
