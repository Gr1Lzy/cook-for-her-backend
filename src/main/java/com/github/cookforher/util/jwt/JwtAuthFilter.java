package com.github.cookforher.util.jwt;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private static final String BEARER_PREFIX = "Bearer ";
  private static final String AUTH_HEADER = "Authorization";

  private final JwtTokenUtil jwtService;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain) throws ServletException, IOException {

    String token = extractToken(request);

    if (token == null || !jwtService.isAccessTokenValid(token)) {
      filterChain.doFilter(request, response);
      return;
    }

    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      setAuthentication(request, token);
    }

    filterChain.doFilter(request, response);
  }

  private String extractToken(HttpServletRequest request) {
    String authHeader = request.getHeader(AUTH_HEADER);

    return (authHeader != null && authHeader.startsWith(BEARER_PREFIX))
        ? authHeader.substring(BEARER_PREFIX.length())
        : null;
  }

  private void setAuthentication(HttpServletRequest request, String token) {
    Long userId = jwtService.extractUserId(token);
    String role = jwtService.extractUserRole(token);

    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
        userId, null, List.of(new SimpleGrantedAuthority(role)));
    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(auth);
  }
}
