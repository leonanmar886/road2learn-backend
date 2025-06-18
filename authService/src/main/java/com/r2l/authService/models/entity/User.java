package com.r2l.authService.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
	@Id
	private UUID id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@Email
	private String email;

	@Column(nullable = false)
	private String password;
}
