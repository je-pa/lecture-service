package com.lectureservice.domain.user.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.lectureservice.api.controller.user.dto.request.UserRequest;
import com.lectureservice.domain.BaseEntity;
import com.lectureservice.domain.user.type.Gender;
import com.lectureservice.domain.user.type.Role;
import com.lectureservice.global.security.service.dto.Authenticatable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User extends BaseEntity implements Authenticatable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column(unique = true, nullable = false, length = 40)
  private String email;

  @Column(nullable = false, length = 60)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Gender gender;

  @Column(nullable = false, length = 14)
  private String phoneNumber;

  @Column(length = 100)
  private String address;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  @Builder
  private User(String email, Gender gender, String password, String phoneNumber,
      String address, Role role) {
    this.email = email;
    this.password = password;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.role = role;
  }

  public static User from(UserRequest request, String encodedPassword){
    return User.builder()
        .email(request.email())
        .password(encodedPassword)
        .gender(request.gender())
        .phoneNumber(request.phoneNumber())
        .address(request.address())
        .role(request.role())
        .build();
  }
}
