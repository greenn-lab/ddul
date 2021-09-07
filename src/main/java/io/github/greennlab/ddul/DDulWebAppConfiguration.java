package io.github.greennlab.ddul;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import io.github.greennlab.ddul.infrastructure.user.annotation.LoggedInArgumentResolver;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class DDulWebAppConfiguration implements WebMvcConfigurer {

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new LoggedInArgumentResolver());
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

    private static final long serialVersionUID = -5634271371186212761L;


    public StringToBooleanDeserializer() {
      super(Boolean.TYPE);
    }

    @Override
    public Boolean deserialize(JsonParser parser, DeserializationContext context)
        throws IOException {
      final String valueAsString = parser.getValueAsString();
      return valueAsString.toLowerCase().matches("true|on|y|yes");
    }

  }

  @SuppressWarnings("java:S110")
  static class MilliToLocalDateTimeDeserializer extends LocalDateTimeDeserializer {

    private static final long serialVersionUID = -7406281879843098525L;


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
