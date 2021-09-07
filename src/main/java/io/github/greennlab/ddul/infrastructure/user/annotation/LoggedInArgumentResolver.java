package io.github.greennlab.ddul.infrastructure.user.annotation;

import io.github.greennlab.ddul.infrastructure.authority.AuthorizedUser;
import io.github.greennlab.ddul.infrastructure.user.User;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.TypeUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoggedInArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter methodParameter) {
    return methodParameter.hasParameterAnnotation(LoggedIn.class);
  }

  @Override
  public Object resolveArgument(@NonNull MethodParameter methodParameter,
      ModelAndViewContainer modelAndViewContainer, @NonNull NativeWebRequest nativeWebRequest,
      WebDataBinderFactory webDataBinderFactory) {

    final SecurityContext context = SecurityContextHolder.getContext();
    final Authentication authentication = context.getAuthentication();

    if (authentication.getPrincipal() instanceof AuthorizedUser) {
      final AuthorizedUser principal = (AuthorizedUser) authentication.getPrincipal();
      final Class<?> parameterType = methodParameter.getParameterType();

      if (AuthorizedUser.class == parameterType) {
        return principal;
      } else if (User.class == parameterType) {
        return principal.getUser();
      }

      final Type genericParameterType = methodParameter.getGenericParameterType();

      if (TypeUtils.isAssignable(List.class, genericParameterType)) {
        return new ArrayList<GrantedAuthority>(principal.getAuthorities());
      } else if (TypeUtils.isAssignable(Set.class, genericParameterType)) {
        return new HashSet<GrantedAuthority>(principal.getAuthorities());
      } else if (TypeUtils.isAssignable(Collection.class, genericParameterType)) {
        return principal.getAuthorities();
      }
    }

    return null;
  }
}
