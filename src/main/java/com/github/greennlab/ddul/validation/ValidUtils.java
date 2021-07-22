package com.github.greennlab.ddul.validation;

import java.util.Map;
import java.util.function.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.util.FieldUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidUtils {

  public static <T> ValidTarget<T> correct(String field) {
    return new ValidTarget<>(field);
  }


  public static class ValidTarget<T> {

    private final String field;
    private T target;

    private ValidTarget(@NonNull String field) {
      this.field = field;
    }

    private ValidTarget(@NonNull String field, @NonNull T target) {
      this.field = field;
      this.target = target;
    }

    public <S> ValidTarget<S> in(@NonNull Map<String, S> group) {
      return new ValidTarget<>(field, group.get(field));
    }

    @SuppressWarnings("unchecked")
    public <S> ValidTarget<S> in(@NonNull Object group) {
      S value;

      try {
        value = (S) FieldUtils.getFieldValue(group, this.field);
      } catch (IllegalAccessException e) {
        value = (S) FieldUtils.getProtectedFieldValue(this.field, group);
      }

      return new ValidTarget<>(this.field, value);
    }

    public void of(Predicate<T> predicate) {
      if (!predicate.test(target)) {
        throw new InvalidFieldException(field, "errorMessage");
      }
    }

    public void of(Predicate<T> predicate, String message) {
      if (!predicate.test(target)) {
        throw new InvalidFieldException(field, message);
      }
    }
  }

}
