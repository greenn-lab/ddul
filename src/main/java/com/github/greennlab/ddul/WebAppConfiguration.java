package com.github.greennlab.ddul;

import java.util.Locale;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class WebAppConfiguration {

  @Bean
  AuditorAware<String> securityLinkageAuditorAware() {
    return () -> {
      final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication == null) {
        return Optional.of("{ghost}");
      }

      final Object principal = authentication.getPrincipal();
      if (principal instanceof User) {
        return Optional.of(((User) principal).getUsername());
      }

      return Optional.of(principal.toString());
    };
  }

  @Bean
  SessionLocaleResolver sessionLocaleResolver() {
    final SessionLocaleResolver resolver = new SessionLocaleResolver();
    resolver.setDefaultLocale(Locale.KOREAN);
    return resolver;
  }

}
