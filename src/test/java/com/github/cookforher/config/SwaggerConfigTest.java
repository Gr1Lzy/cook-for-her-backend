package com.github.cookforher.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SwaggerConfigTest {

  private final SwaggerConfig swaggerConfig = new SwaggerConfig();

  @Test
  void customOpenApi_shouldReturnConfiguredOpenAPI() {
    // When
    OpenAPI result = swaggerConfig.customOpenApi();

    // Then
    assertNotNull(result);
    assertNotNull(result.getSecurity());
    assertFalse(result.getSecurity().isEmpty());

    SecurityRequirement securityRequirement = result.getSecurity().getFirst();
    assertTrue(securityRequirement.containsKey("Authentication Bearer"));

    assertNotNull(result.getComponents());
    assertNotNull(result.getComponents().getSecuritySchemes());
    assertTrue(result.getComponents().getSecuritySchemes().containsKey("Authentication Bearer"));

    SecurityScheme scheme = result.getComponents().getSecuritySchemes().get("Authentication Bearer");
    assertEquals(SecurityScheme.Type.HTTP, scheme.getType());
    assertEquals("bearer", scheme.getScheme());
    assertEquals("JWT", scheme.getBearerFormat());
    assertEquals(SecurityScheme.In.HEADER, scheme.getIn());
  }
}

