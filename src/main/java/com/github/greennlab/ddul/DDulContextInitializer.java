package com.github.greennlab.ddul;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.Ordered;

public class DDulContextInitializer implements
    ApplicationContextInitializer<AnnotationConfigServletWebServerApplicationContext>, Ordered {

  @Override
  public void initialize(AnnotationConfigServletWebServerApplicationContext context) {
    context.scan(Application.PACKAGE);
  }

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE;
  }

}
