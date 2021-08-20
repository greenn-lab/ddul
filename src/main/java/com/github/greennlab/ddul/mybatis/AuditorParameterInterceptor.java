package com.github.greennlab.ddul.mybatis;

import com.github.greennlab.ddul.authority.AuthorizedUser;
import java.sql.Connection;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

@Intercepts({
    @Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class})
})
@Slf4j
public class AuditorParameterInterceptor implements Interceptor {

  public Object intercept(Invocation invocation) throws Throwable {
    final StatementHandler handler = (StatementHandler) invocation.getTarget();
    final BoundSql boundSql = handler.getBoundSql();

    boundSql.setAdditionalParameter("_", AuthorizedUser.currently());

    return invocation.proceed();
  }

  @Override
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  @Override
  public void setProperties(Properties properties) {
    // empty
  }

}
