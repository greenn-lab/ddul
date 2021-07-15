package com.github.greennlab.ddul;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Locale;
import java.util.Optional;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class DDulWebAppConfiguration {

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

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
    return builder ->
        builder.deserializers(
            new StringToBooleanDeserializer(),
            new MilliToLocalDateTimeDeserializer()
        );
  }

  static class StringToBooleanDeserializer extends StdScalarDeserializer<Boolean> {

    public StringToBooleanDeserializer() {
      super(Boolean.TYPE);
    }

    @Override
    public Boolean deserialize(JsonParser parser, DeserializationContext context)
        throws IOException {
      final String valueAsString = parser.getValueAsString();
      return valueAsString.toLowerCase().matches("on|y|yes");
    }

  }

  @SuppressWarnings("java:S110")
  static class MilliToLocalDateTimeDeserializer extends LocalDateTimeDeserializer {

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context)
        throws IOException {
      if (parser.hasToken(JsonToken.VALUE_NUMBER_INT)) {
        long value = parser.getValueAsLong();
        return LocalDateTime
            .ofInstant(Instant.ofEpochMilli(value), OffsetDateTime.now().getOffset());
      }

      return super.deserialize(parser, context);
    }

  }

}
