package io.github.greennlab.ddul.graphql;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@JsonIncludeProperties({"message", "extensions"})
@EqualsAndHashCode(callSuper = true)
public class GraphQLMutationNotValidException
    extends BindException
    implements GraphQLError {

  private final Map<String, Object> errors = new HashMap<>();

  public GraphQLMutationNotValidException(BindingResult bindingResult) {
    super(bindingResult);
  }

  @Override
  @NonNull
  public String getMessage() {
    return "Validation Error";
  }

  @Override
  public Map<String, Object> getExtensions() {
    final List<ObjectError> allErrors = super.getAllErrors();

    for (final ObjectError error : allErrors) {
      final String name = error instanceof FieldError
          ? ((FieldError) error).getField()
          : "$";

      compositeMessages(error, name);
    }

    return errors;
  }

  private void compositeMessages(ObjectError error, String field) {
      if (!errors.containsKey(field)) {
          errors.put(field, new String[]{error.getDefaultMessage()});
      } else {
          final String[] messages = (String[]) errors.get(field);
          final String[] added = new String[messages.length + 1];

          System.arraycopy(messages, 0, added, 0, messages.length);
          added[added.length - 1] = error.getDefaultMessage();

          errors.put(field, added);
      }
  }

  @Override
  public List<SourceLocation> getLocations() {
    return Collections.emptyList();
  }

  @Override
  public ErrorType getErrorType() {
    return null;
  }

}
