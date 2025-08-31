package com.github.cookforher.util.jwt;

import com.github.cookforher.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

  private static final String ROLE = "role";
  private static final String ID = "id";
  private static final String TOKEN_TYPE = "token_type";
  private static final String ACCESS_TOKEN = "access";
  private static final String REFRESH_TOKEN = "refresh";

  @Value("${jwt.token.secret}")
  private String tokenSecret;

  @Value("${jwt.access.expiration}")
  private Duration accessTokenExpiration;

  @Value("${jwt.refresh.expiration}")
  private Duration refreshTokenExpiration;

  public Long extractUserId(String token) {
    return extractClaim(token, claims -> claims.get(ID, Long.class));
  }

  public String extractUserRole(String token) {
    return extractClaim(token, claims -> claims.get(ROLE, String.class));
  }

  public boolean isAccessTokenValid(String token) {
    try {
      return isValidToken(token, ACCESS_TOKEN);
    } catch (Exception e) {
      return false;
    }
  }

  public boolean isRefreshTokenValid(String token) {
    return isValidToken(token, REFRESH_TOKEN);
  }

  public String generateAccessToken(UserDetails userDetails) {
    return generateToken(userDetails, accessTokenExpiration, ACCESS_TOKEN);
  }

  public String generateRefreshToken(UserDetails userDetails) {
    return generateToken(userDetails, refreshTokenExpiration, REFRESH_TOKEN);
  }

  private boolean isValidToken(String token, String expectedType) {
    return !isTokenExpired(token)
        && expectedType.equals(extractClaim(token, claims -> claims.get(TOKEN_TYPE, String.class)));
  }

  private String generateToken(UserDetails userDetails, Duration expiration, String tokenType) {
    Map<String, Object> claims = new HashMap<>();

    claims.put(TOKEN_TYPE, tokenType);

    if (userDetails instanceof User user) {
      claims.put(ID, user.getId());

      if (tokenType.equals(ACCESS_TOKEN)) {
        claims.put(ROLE, user.getRole());
      }
    }

    return Jwts.builder()
        .claims(claims)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + expiration.toMillis()))
        .signWith(getSigningKey())
        .compact();
  }

  private <T> T extractClaim(String token, Function<Claims, T> resolver) {
    return resolver.apply(extractAllClaims(token));
  }

  private boolean isTokenExpired(String token) {
    return extractClaim(token, Claims::getExpiration)
        .before(new Date());
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokenSecret));
  }
}
