package com.r2l.userService.models.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user-profile")
public class UserProfile {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, unique = true)
  private UUID userId;

  @Column(nullable = false)
  private String username;
}
