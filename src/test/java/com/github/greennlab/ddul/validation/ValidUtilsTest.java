package com.github.greennlab.ddul.validation;

import static com.github.greennlab.ddul.validation.ValidUtils.correct;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.greennlab.ddul.validation.ValidUtils.ValidTarget;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.validation.FieldError;

class ValidUtilsTest {

  @Test
  void shouldCorrect() {
    Map<String, Integer> given = new HashMap<>();
    given.put("a", 1);
    given.put("b", 2);
    given.put("c", 3);

    correct("a").in(given).of(i -> i > 0);
  }

  @Test
  void shouldIncorrect() {
    final String target = "a";
    Map<String, Integer> given = new HashMap<>();
    given.put(target, 1);
    given.put("b", 2);
    given.put("c", 3);

    final ValidTarget<Integer> a = correct(target).in(given);
    final InvalidFieldException exception = assertThrows(
        InvalidFieldException.class,
        () -> a.of(i -> i < 0)
    );

    final FieldError error = exception.getError();
    assertEquals(error.getField(), target);
  }

}
