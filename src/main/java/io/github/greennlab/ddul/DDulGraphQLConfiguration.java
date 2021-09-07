package io.github.greennlab.ddul;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.kickstart.execution.error.DefaultGraphQLErrorHandler;
import graphql.kickstart.execution.error.GenericGraphQLError;
import graphql.kickstart.execution.error.GraphQLErrorHandler;
import graphql.kickstart.tools.ObjectMapperConfigurer;
import graphql.kickstart.tools.SchemaParserOptions;
import graphql.language.IntValue;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import io.github.greennlab.ddul.DDulMessageConfiguration.ExceptionMessageSource;
import io.github.greennlab.ddul.graphql.GraphQLMutationNotValidException;
import java.lang.reflect.UndeclaredThrowableException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Configuration
@RequiredArgsConstructor
public class DDulGraphQLConfiguration {

  private final ExceptionMessageSource exceptionMessageSource;

  @Bean
  SchemaParserOptions schemaParserOptions() {
    final ObjectMapperConfigurer objectMapperConfigurer =
        (mapper, context) -> mapper.registerModule(new JavaTimeModule());

    return SchemaParserOptions.newOptions()
        .objectMapperConfigurer(objectMapperConfigurer)
        .build();
  }

  @Bean
  GraphQLErrorHandler errorHandler() {
    return new DefaultGraphQLErrorHandler() {
      @Override
      public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        final List<GraphQLError> handledErrors = new ArrayList<>(errors.size());

        for (GraphQLError error : errors) {
          if (error instanceof ExceptionWhileDataFetching) {
            final Throwable exception = ((ExceptionWhileDataFetching) error).getException();

            if (!validateExceptionHandled(exception, handledErrors)) {
              final String message = exceptionMessageSource
                  .get(exception.getClass())
                  .orElse(exception.getMessage());

              handledErrors.add(new GenericGraphQLError(message));
            }
          } else {
            handledErrors.add(error);
          }
        }

        return handledErrors;
      }
    };
  }

  private boolean validateExceptionHandled(Throwable exception, List<GraphQLError> handledErrors) {
    if (exception instanceof UndeclaredThrowableException) {
      final Throwable notValidException =
          ((UndeclaredThrowableException) exception).getUndeclaredThrowable();

      if (notValidException instanceof GraphQLMutationNotValidException) {
        return handledErrors.add((GraphQLMutationNotValidException) notValidException);
      }
    }

    return false;
  }

  @Bean
  GraphQLScalarType scalarDateTime() {
    return GraphQLScalarType.newScalar()
        .name("DateTime")
        .description("simple date-time type")
        .coercing(new Coercing<LocalDateTime, String>() {
          @Override
          public String serialize(@NonNull Object dataFetcherResult)
              throws CoercingSerializeException {
            return dataFetcherResult.toString();
          }

          @Override
          @NonNull
          public LocalDateTime parseValue(@NonNull Object input)
              throws CoercingParseValueException {
            return LocalDateTime.parse(input.toString());
          }

          @Override
          @NonNull
          public LocalDateTime parseLiteral(@NonNull Object input)
              throws CoercingParseLiteralException {
            if (input instanceof StringValue) {
              final StringValue value = (StringValue) input;
              return LocalDateTime.parse(value.getValue());
            }

            throw new IllegalArgumentException();
          }
        })
        .build();
  }

  @Bean
  GraphQLScalarType scalarDate() {

    return GraphQLScalarType.newScalar()
        .name("Date")
        .description("simple date type")
        .coercing(new Coercing<LocalDate, String>() {
                    @Override
                    public String serialize(@NonNull Object dataFetcherResult)
                        throws CoercingSerializeException {
                      return dataFetcherResult.toString();
                    }

                    @Override
                    @NonNull
                    public LocalDate parseValue(@NonNull Object input) throws CoercingParseValueException {
                      return LocalDate.parse(input.toString());
                    }

                    @Override
                    @NonNull
                    public LocalDate parseLiteral(@NonNull Object input)
                        throws CoercingParseLiteralException {
                      if (input instanceof StringValue) {
                        final StringValue value = (StringValue) input;
                        return LocalDate.parse(value.getValue());
                      }

                      throw new IllegalArgumentException();
                    }
                  }
        )
        .build();
  }

  @Value("${spring.data.web.pageable.default-page-size}")
  private int defaultPageSize;

  @Bean
  GraphQLScalarType scalarPageable() {
    return GraphQLScalarType.newScalar()
        .name("Pageable")
        .coercing(new Coercing<Pageable, String>() {
                    private final Pattern pagePattern = Pattern.compile("^(\\d+)(?:/(\\d+))?$");

                    @Override
                    public String serialize(@NonNull Object dataFetcherResult)
                        throws CoercingSerializeException {
                      return dataFetcherResult.toString();
                    }

                    @Override
                    @NonNull
                    public Pageable parseValue(@NonNull Object input) throws CoercingParseValueException {
                      final Matcher matcher = pagePattern.matcher(input.toString());

                      if (!matcher.find()) {
                        throw new IllegalArgumentException("{page number}/{size number} ex) 1/10");
                      }

                      final int page = Integer.parseInt(matcher.group(1));
                      final int size = null == matcher.group(2)
                          ? defaultPageSize
                          : Integer.parseInt(matcher.group(2));

                      return PageRequest.of(page, size);
                    }

                    @Override
                    @NonNull
                    public Pageable parseLiteral(@NonNull Object input) throws CoercingParseLiteralException {
                      if (input instanceof StringValue) {
                        final StringValue value = (StringValue) input;
                        return parseValue(value.getValue());
                      } else if (input instanceof IntValue) {
                        return parseValue(((IntValue) input).getValue().toString());
                      }

                      throw new IllegalArgumentException();
                    }
                  }
        )
        .build();
  }

}
