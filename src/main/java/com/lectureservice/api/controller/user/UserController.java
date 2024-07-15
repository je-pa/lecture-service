package com.lectureservice.api.controller.user;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.lectureservice.api.controller.user.dto.request.SigninRequest;
import com.lectureservice.api.controller.user.dto.request.UserRequest;
import com.lectureservice.api.service.user.UserService;
import com.lectureservice.global.security.anotation.CurrentUserId;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;
  private final static String ACCESS_TOKEN_KEY = AUTHORIZATION;

  @Operation(summary = "회원가입", description = "회원가입을 합니다.")
  @PostMapping("/signup")
  public ResponseEntity<Void> signup(@Valid @RequestBody UserRequest request) {
    userService.signup(request);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "로그인", description = "로그인을 합니다.")
  @PostMapping("/signin")
  public ResponseEntity<Void> signin(
      @Valid @RequestBody SigninRequest request,
      HttpServletResponse response) {
    response.addHeader(ACCESS_TOKEN_KEY, userService.signin(request).token());
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "회원 탈퇴", description = "탈퇴합니다.")
  @DeleteMapping
  public ResponseEntity<Void> delete(@CurrentUserId Long currentUserId){
    userService.delete(currentUserId);
    return ResponseEntity.ok().build();
  }

}
