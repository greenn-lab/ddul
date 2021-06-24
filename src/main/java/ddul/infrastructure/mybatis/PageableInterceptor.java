package ddul.infrastructure.mybatis;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Intercepts({
    @Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class})
})
@Slf4j
public class PageableInterceptor implements Interceptor {

  public Object intercept(Invocation invocation) throws Throwable {
    final StatementHandler handler = (StatementHandler) invocation.getTarget();
    final BoundSql boundSql = handler.getBoundSql();
    final Object parameter = boundSql.getParameterObject();

    if (parameter instanceof MapperMethod.ParamMap) {
      final MapperMethod.ParamMap<?> parameterMap = (MapperMethod.ParamMap<?>) parameter;

      for (final Map.Entry<String, ?> item : parameterMap.entrySet()) {
        if (item.getValue() instanceof Pageable) {
//          MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
//          MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");

//          final PageImpl page = processPagination(boundSql, parameter, (Pageable) item.getValue());
//          return page;
        }
      }
    }

    return invocation.proceed();
  }

  private PageImpl processPagination(final BoundSql boundSql, final Object parameter,
      final Pageable pageable) {
    final PageRequest pageRequest = PageRequest
        .of(pageable.getPageNumber(), pageable.getPageSize());

    return new PageImpl(null, pageable, counting(boundSql.getSql(), pageable));
  }

  private long counting(String originalSql, Pageable pageable) {
    return 0;
  }

  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  public void setProperties(Properties properties) {
  }

}
