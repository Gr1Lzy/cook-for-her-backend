package com.github.cookforher.config;

import com.github.cookforher.exception.handler.CustomAccessDeniedHandler;
import com.github.cookforher.exception.handler.CustomAuthenticationEntryPoint;
import com.github.cookforher.util.jwt.JwtAuthFilter;
import com.github.cookforher.util.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtTokenUtil jwtTokenUtil;
  private final CustomAccessDeniedHandler accessDeniedHandler;
  private final CustomAuthenticationEntryPoint authenticationEntryPoint;

  private static final String ADMIN = "ROLE_ADMIN";
  private static final String USER = "ROLE_USER";
  private static final String[] SWAGGER = {
      "/swagger-ui/**",
      "/v3/api-docs/**",
      "/swagger-ui.html"
  };

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(AbstractHttpConfigurer::disable)

        .sessionManagement(session -> session
            .sessionCreationPolicy(STATELESS))

        .authorizeHttpRequests(request -> request
            .requestMatchers(SWAGGER).permitAll()
            .requestMatchers("/v1/auth/**").permitAll()
            .requestMatchers("/v1/admin/**").hasAuthority(ADMIN)
            .requestMatchers("/v1/user/**").hasAnyAuthority(USER, ADMIN)
            .requestMatchers("/v1/recipe/**").hasAnyAuthority(USER, ADMIN)
            .anyRequest().authenticated()
        )

        .exceptionHandling(exceptions -> exceptions
            .accessDeniedHandler(accessDeniedHandler)
            .authenticationEntryPoint(authenticationEntryPoint)
        )

        .addFilterBefore(new JwtAuthFilter(jwtTokenUtil), UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }
}
