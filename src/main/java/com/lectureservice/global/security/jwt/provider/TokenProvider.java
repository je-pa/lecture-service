package com.lectureservice.global.security.jwt.provider;

import com.lectureservice.global.exception.CustomException;
import com.lectureservice.global.exception.ExceptionCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenProvider {

  private static final long TOKEN_EXPIRE_TIME = 3_600_000L; // 1000 * 60 * 60 // 1hour
  private static final String KEY_ROLES = "roles";
  private static final MacAlgorithm SIGNATURE_ALGORITHM = SIG.HS256;
  private static final String TOKEN_PREFIX = "Bearer ";

  private final UserDetailsService userDetailsService;

  private SecretKey key;
  @Value("${spring.jwt.secret}")
  private String secretKey;

  public String generateToken(String username, List<String> roles) {
    Claims claims = Jwts.claims().subject(username).add(KEY_ROLES, roles).build();

    Date now = new Date();
    Date expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

    return TOKEN_PREFIX +
        Jwts.builder()
        .claims(claims)
        .issuedAt(now)
        .expiration(expiredDate)
        .signWith(key, SIGNATURE_ALGORITHM)
        .compact();
  }

  public Authentication getAuthentication(String token) {
    if (!StringUtils.hasText(token) || !token.startsWith(TOKEN_PREFIX)) {
      return null;
    }
    token = token.substring(TOKEN_PREFIX.length());
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(this.getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public boolean validAccessToken(String token) {
    if (!StringUtils.hasText(token) || !token.startsWith(TOKEN_PREFIX)) {
      return false;
    }

    return validateToken(token.substring(TOKEN_PREFIX.length()));
  }

  private boolean validateToken(String token) {
    return !getExpiration(token).before(new Date());
  }

  private Date getExpiration(String token) {
    if (!StringUtils.hasText(token)) {
      throw new CustomException(ExceptionCode.TOKEN_EXPIRED);
    }

    return this.parseClaims(token).getExpiration();
  }

  private String getUsername(String token) {
    return this.parseClaims(token).getSubject();
  }

  private Claims parseClaims(String token) {
    try {
      return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    } catch (SecurityException | MalformedJwtException | SignatureException e) {
      throw new CustomException(ExceptionCode.INVALID_JWT_SIGNATURE);
    } catch (ExpiredJwtException e) {
      throw new CustomException(ExceptionCode.TOKEN_EXPIRED);
    } catch (UnsupportedJwtException e) {
      throw new CustomException(ExceptionCode.UNSUPPORTED_JWT_TOKEN);
    } catch (IllegalArgumentException e) {
      throw new CustomException(ExceptionCode.JWT_CLAIMS_EMPTY);
    }
  }

  @PostConstruct
  private void setKey() {
    key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secretKey));
  }
}