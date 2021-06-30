package com.github.greennlab.ddul;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class MessageConfiguration {

  @Bean
  ReloadableResourceBundleMessageSource messageSource(
      @Value("${spring.messages.cache-duration:-1}") int cacheDuration
  ) throws IOException {
    final ReloadableResourceBundleMessageSource
        source = new ReloadableResourceBundleMessageSource();

    source.setBasenames(findOutBasename());
    source.setCacheSeconds(cacheDuration);
    source.setUseCodeAsDefaultMessage(true);

    return source;
  }

  private String[] findOutBasename() throws IOException {
    final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    final Resource[] resources = resolver.getResources("classpath:i18n/**/*.xml");
    final List<String> sources = new ArrayList<>();

    String group = "*";
    for (Resource res : resources) {
      final String filename = res.getFilename();
      assert filename != null;

      if (!filename.startsWith(group)) {
        group = filename.substring(0, filename.lastIndexOf("."));
        sources.add("classpath:i18n/" + group);
      }
    }

    return sources.toArray(new String[0]);
  }

  @Bean
  ExceptionMessageSource exceptionMessageSource(
      ReloadableResourceBundleMessageSource messageSource
  ) {
    return new ExceptionMessageSource() {
      public Optional<String> get(Class<? extends Throwable> clazz) {
        final String className = clazz.getName();
        final Locale locale = Locale.getDefault();

        try {
          final String message = messageSource.getMessage(className, null, locale);
          if (message.equals(className)) {
            throw new NoSuchMessageException(className);
          }

          return Optional.of(message);
        } catch (NoSuchMessageException e) {
          if (Object.class != clazz.getSuperclass()) {
            @SuppressWarnings("unchecked") final Class<? extends Throwable> superclass
                = (Class<? extends Throwable>) clazz.getSuperclass();

            return get(superclass);
          }
        }

        return Optional.empty();
      }
    };
  }


  public interface ExceptionMessageSource {

    Optional<String> get(Class<? extends Throwable> clazz);

  }

}
