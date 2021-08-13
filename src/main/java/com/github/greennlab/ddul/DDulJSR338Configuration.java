package com.github.greennlab.ddul;

import com.github.greennlab.ddul.user.User;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class DDulJSR338Configuration {

  private static final User ghost = new User();

  static {
    ghost.setUsername("{ghost}");
  }


  @Bean
  AuditorAware<String> securityLinkageAuditorAware() {
    return () -> {
      final Optional<User> authenticated = User.authenticated();
      return Optional.of(authenticated.orElse(ghost).getUsername());
    };
  }

}
