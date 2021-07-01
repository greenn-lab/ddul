package com.github.greennlab.ddul;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class WebAppConfiguration {

  @Bean
  SessionLocaleResolver sessionLocaleResolver() {
    final SessionLocaleResolver resolver = new SessionLocaleResolver();
    resolver.setDefaultLocale(Locale.KOREAN);
    return resolver;
  }

}
