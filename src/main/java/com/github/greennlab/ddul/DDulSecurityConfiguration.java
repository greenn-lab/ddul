package com.github.greennlab.ddul;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@ConditionalOnMissingBean(
    name = {"springSecurityFilterChain", "securityAutoConfiguration"}
)
@Order
public class DDulSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .headers()
        .frameOptions().sameOrigin()
        .and()

        .anonymous().principal("Anonymous").authorities("GUEST").and()

        .authorizeRequests()
        .antMatchers("/*").permitAll()
    ;
  }

}
