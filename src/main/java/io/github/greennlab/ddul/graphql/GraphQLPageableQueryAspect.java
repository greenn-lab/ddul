package io.github.greennlab.ddul.graphql;

import javax.servlet.http.HttpServletResponse;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.domain.Page;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//@Component
//@Aspect
public class GraphQLPageableQueryAspect {

  @Pointcut("@target(org.springframework.stereotype.Service) || @target(org.springframework.stereotype.Component)")
  public void componentClass() {
    // use to pointcut
  }

  @Pointcut("execution(org.springframework.data.domain.Page *.*(..))")
  public void pageReturningMethods() {
    // use to pointcut
  }

  @AfterReturning(
      pointcut = "componentClass() && pageReturningMethods()",
      returning = "paged"
  )
  public void after(Page<?> paged) {
    final ServletRequestAttributes requestAttributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

    assert requestAttributes != null;
    final HttpServletResponse response = requestAttributes.getResponse();

    assert response != null;
    response.setHeader("Paginate-Index", String.valueOf(paged.getNumber() + 1));
    response.setHeader("Paginate-Total", String.valueOf(paged.getTotalElements()));
    response.setHeader("Paginate-Pages", String.valueOf(paged.getTotalPages()));
  }

}
