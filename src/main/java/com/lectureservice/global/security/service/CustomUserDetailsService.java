package com.lectureservice.global.security.service;

import static com.lectureservice.global.exception.ExceptionCode.USER_NOT_FOUND;

import com.lectureservice.domain.user.repository.UserRepository;
import com.lectureservice.global.security.service.dto.CustomUserDetails;
import com.lectureservice.global.security.service.dto.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return new CustomUserDetails(getUserDetailsDomain(username));
  }

  private UserDetailsDto getUserDetailsDomain(String username) {
    return UserDetailsDto.from(userRepository.findById(Long.valueOf(username))
        .orElseThrow(() -> new UsernameNotFoundException(
            USER_NOT_FOUND.getMessage()
        )));
  }
}