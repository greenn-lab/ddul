package io.github.greennlab.ddul;

import io.github.greennlab.ddul.infrastructure.authority.AuthorizedUser;
import io.github.greennlab.ddul.infrastructure.user.User;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class DDulJSR338Configuration {

  private static final User ghostUser = new User();

  static {
    ghostUser.setUsername("{ghost}");
  }


  @Bean
  AuditorAware<String> securityLinkageAuditorAware() {
    return () -> {
      final String username = AuthorizedUser.currently().orElse(ghostUser).getUsername();
      return Optional.of(username);
    };
  }

}
