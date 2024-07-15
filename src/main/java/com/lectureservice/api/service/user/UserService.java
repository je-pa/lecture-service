package com.lectureservice.api.service.user;

import static com.lectureservice.global.exception.ExceptionCode.EMAIL_CONFLICT;
import static com.lectureservice.global.exception.ExceptionCode.PASSWORD_MISMATCH;
import static com.lectureservice.global.exception.ExceptionCode.USER_NOT_FOUND;

import com.lectureservice.api.controller.user.dto.request.SigninRequest;
import com.lectureservice.api.controller.user.dto.request.UserRequest;
import com.lectureservice.api.controller.user.dto.response.SigninResponse;
import com.lectureservice.domain.comment.repository.CommentRepository;
import com.lectureservice.domain.user.entity.User;
import com.lectureservice.domain.user.repository.UserRepository;
import com.lectureservice.global.exception.CustomException;
import com.lectureservice.global.security.jwt.provider.TokenProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;
  private final CommentRepository commentRepository;

  public void signup(UserRequest request) {
    if (userRepository.existsByEmail(request.email())) {
      throw new CustomException(EMAIL_CONFLICT);
    }

    userRepository.save(
        User.from(request, passwordEncoder.encode(request.password())));
  }

  @Transactional(readOnly = true)
  public SigninResponse signin(SigninRequest request) {
    User user = userRepository.findByEmail(request.email())
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

    if (!passwordEncoder.matches(request.password(), user.getPassword())) {
      throw new CustomException(PASSWORD_MISMATCH);
    }

    return SigninResponse.from(
        tokenProvider.generateToken(user.getId().toString(), List.of(user.getRole().name())));
  }

  @Transactional
  public void delete(Long userId) {
    User user = findUserById(userId);
    commentRepository.deleteAllInBatchByAuthor(user);
    userRepository.delete(user);
  }

  private User findUserById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
  }
}
