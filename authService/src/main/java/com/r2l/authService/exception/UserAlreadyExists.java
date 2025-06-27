package com.r2l.authService.exception;

public class UserAlreadyExists extends RuntimeException {
	public UserAlreadyExists(String message) {
		super(message);
	}
}
