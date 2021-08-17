package com.github.greennlab.ddul;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

import com.github.greennlab.ddul.user.service.DDulUserService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.DelegatingFilterProxy;

@Profile('!' + Application.PRODUCTION)
@Configuration
@EnableWebSecurity
@Order
@RequiredArgsConstructor
public class DDulSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final DDulUserService userService;


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

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService);
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public DelegatingFilterProxy bypass() {
    return new DelegatingFilterProxy() {
      @Override
      public void doFilter(ServletRequest request, ServletResponse response,
          FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        if (null == httpServletRequest.getUserPrincipal()) {
          AbstractAuthenticationToken token =
              new UsernamePasswordAuthenticationToken("tester", "test123$");

          try {
            token =
                (AbstractAuthenticationToken) authenticationManager().authenticate(token);
          } catch (Exception e) {
            throw new ServletException(e);
          }

          final SecurityContext context = SecurityContextHolder.getContext();
          context.setAuthentication(token);

          final HttpSession session = httpServletRequest.getSession(true);
          session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, context);
        }

        chain.doFilter(request, response);
      }
    };
  }

}
