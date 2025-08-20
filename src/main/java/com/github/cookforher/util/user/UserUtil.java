package com.github.cookforher.util.user;

import com.github.cookforher.exception.custom.AuthenticationException;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class UserUtil {
  private static final String ANONYMOUS_USER = "anonymousUser";

  public static Long getCurrentUserId() {
    if (isAnonymousUser()) {
      throw new AuthenticationException("Anonymous user is not allowed to access this resource");
    }

    return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  private boolean isAnonymousUser() {
    String user = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    return user.equals(ANONYMOUS_USER);
  }
}
