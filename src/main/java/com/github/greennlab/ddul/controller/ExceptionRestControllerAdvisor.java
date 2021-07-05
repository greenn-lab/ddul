package com.github.greennlab.ddul.controller;

import com.github.greennlab.ddul.Application;
import com.github.greennlab.ddul.MessageConfiguration.ExceptionMessageSource;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionRestControllerAdvisor {

  private final ExceptionMessageSource exceptionMessageSource;
  private final Environment environment;

  private boolean notProduction;


  @PostConstruct
  public void setup() {
    final Profiles production = Profiles.of(Application.PRODUCTION);
    notProduction = !environment.acceptsProfiles(production);
  }


  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  List<ObjectError> methodArgumentNotValidException(
      MethodArgumentNotValidException exception) {
    return exception.getAllErrors();
  }

  @ExceptionHandler(Throwable.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  Object handleAllOfThrowable(
      Throwable throwable) {

    if (notProduction) {
      logger.error(throwable.getMessage(), throwable);
    }

    return exceptionMessageSource.get(throwable.getClass());
  }


}
