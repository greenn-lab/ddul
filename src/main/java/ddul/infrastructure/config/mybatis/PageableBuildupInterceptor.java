package ddul.infrastructure.config.mybatis;

import ddul.sample.mybatis.MybatisTestDao;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@Intercepts({
    @Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
    @Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class,
            CacheKey.class, BoundSql.class})

})
@Slf4j
public class PageableBuildupInterceptor implements Interceptor {

  protected static final ThreadLocal<PageableVariable> PAGEABLE_VARIABLE = new ThreadLocal<>();

  public Object intercept(Invocation invocation) throws Throwable {
    final MappedStatement ms = (MappedStatement) invocation.getArgs()[0];

    if (SqlCommandType.SELECT == ms.getSqlCommandType()) {
      Object pageableParameter = null;

      if (invocation.getArgs()[1] instanceof Pageable) {
        pageableParameter = invocation.getArgs()[1];
      } else if (invocation.getArgs()[1] instanceof MapperMethod.ParamMap) {
        pageableParameter = pageableParameterFromParamMap(invocation);
      }

      if (null != pageableParameter) {
        final PageableVariable variable = new PageableVariable((Pageable) pageableParameter);
        variable.setMappedStatement(ms);
        variable.setParameter(invocation.getArgs()[1]);
        PAGEABLE_VARIABLE.set(variable);

        try {
          final List<?> list = (List<?>) invocation.proceed();
          final Pageable pageable = variable.pageable;

          return Collections.singletonList(
              new PageImpl<>(list, pageable, variable.total
              )
          );
        } finally {
          PAGEABLE_VARIABLE.remove();
        }
      }
    }

    return invocation.proceed();
  }

  private Object pageableParameterFromParamMap(Invocation invocation) {
    final ParamMap<?> parameters = (ParamMap<?>) invocation.getArgs()[1];

    Object pageableParameter = null;
    Object notAPageableParameter = null;

    for (final Map.Entry<String, ?> param : parameters.entrySet()) {
      final Object value = param.getValue();
      if (value instanceof Pageable) {
        pageableParameter = value;
      } else {
        notAPageableParameter = value;
      }
    }

    if (pageableParameter != null && notAPageableParameter != null && parameters.size() == 4) {
      invocation.getArgs()[1] = notAPageableParameter;
    }

    return pageableParameter;
  }

  @Override
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  @Override
  public void setProperties(Properties properties) {
    // empty
    logger.info("props", properties);
  }


  @Getter
  @Setter
  @RequiredArgsConstructor
  static class PageableVariable {

    private final Pageable pageable;
    private long total = 0;
    private MappedStatement mappedStatement;
    private Object parameter;

    public int getPageSize() {
      return pageable.getPageSize();
    }

    public int getPageNumber() {
      return pageable.getPageNumber();
    }
  }

}
