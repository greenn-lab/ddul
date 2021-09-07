package io.github.greennlab.ddul.graphql;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

@Component
@Aspect
@RequiredArgsConstructor
public class GraphQLMutationValidAspect {

  private final SpringValidatorAdapter validator;


  @Pointcut("@target(org.springframework.stereotype.Service) || @target(org.springframework.stereotype.Component)")
  public void componentClass() {
    // use to pointcut
  }

  @Pointcut("execution(* *(.., @javax.validation.Valid (*), ..))")
  public void hasValidArgumentMethods() {
    // use to pointcut
  }

  @Pointcut("execution(* *(.., @org.springframework.validation.annotation.Validated (*), ..))")
  public void hasValidatedArgumentMethods() {
    // use to pointcut
  }

  @Before("componentClass() && (hasValidArgumentMethods() || hasValidatedArgumentMethods())")
  public void before(JoinPoint joinPoint)
      throws GraphQLMutationNotValidException {
    final Signature signature = joinPoint.getSignature();
    final Method method = ((MethodSignature) signature).getMethod();
    final Parameter[] parameters = method.getParameters();
    final Object[] arguments = joinPoint.getArgs();

    for (int i = 0; i < arguments.length; i++) {
      validate(parameters[i], arguments[i]);
    }
  }

  private void validate(Parameter parameter, Object target)
      throws GraphQLMutationNotValidException {
    Object[] groups = null;

    if (parameter.isAnnotationPresent(Valid.class)) {
      groups = new Class[0];
    }

    if (parameter.isAnnotationPresent(Validated.class)) {
      groups = parameter.getAnnotation(Validated.class).value();
    }

    if (groups == null) {
      return;
    }

    final BindingResult errors = new BeanPropertyBindingResult(target, "");
    validator.validate(target, errors, groups);

    if (errors.hasErrors()) {
      throw new GraphQLMutationNotValidException(errors);
    }
  }

}
