package io.github.greennlab.ddul.infrastructure.sample.validation;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample/validation")
public class ValidationTestController {

  @PostMapping
  public Object test(@Validated @RequestBody ValidationTest test) {
    return test;
  }

}
