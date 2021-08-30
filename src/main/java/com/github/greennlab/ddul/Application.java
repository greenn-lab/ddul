package com.github.greennlab.ddul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  public static final String PACKAGE = Application.class.getPackage().getName();

  public static final String PRODUCTION = "production";

  public static final String DB_PREFIX = "";


  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
