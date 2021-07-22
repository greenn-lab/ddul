package com.github.greennlab.ddul.validation;

import com.github.greennlab.ddul.Application;
import com.github.greennlab.ddul.DDulMessageConfiguration.ExceptionMessageSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class DDulExceptionRestControllerAdvisor {

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

  @ExceptionHandler(InvalidFieldException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  FieldError InvalidFieldException(
      InvalidFieldException exception) {
    return exception.getError();
  }

  @ExceptionHandler(Throwable.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  Object handleAllOfThrowable(
      Throwable throwable) {

    if (notProduction) {
      logger.error(throwable.getMessage(), throwable);
    }

    final Map<String, Object> message = new HashMap<>(2);
    message.put("message", exceptionMessageSource.get(throwable.getClass()));
    message.put("exception", throwable.getMessage());

    return message;
  }


}
