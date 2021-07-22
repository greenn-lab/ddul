package com.github.greennlab.ddul.validation;

import lombok.Getter;
import org.springframework.validation.FieldError;

public class InvalidFieldException extends RuntimeException {

  @Getter
  private final FieldError error;

  public InvalidFieldException(String field, String errorMessage) {
    error = new FieldError("", field, errorMessage);
  }
}
