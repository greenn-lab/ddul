package ddul.infrastructure.mybatis;

import static ddul.infrastructure.mybatis.PageableBuildupInterceptor.PAGEABLE_VARIABLE;

import ddul.infrastructure.mybatis.PageableBuildupInterceptor.PageableVariable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.springframework.data.util.ReflectionUtils;

@Intercepts({
    @Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class})
})
@Slf4j
public class PageableExecuteInterceptor implements Interceptor {

  public Object intercept(Invocation invocation) throws Throwable {
    if (null != PAGEABLE_VARIABLE.get()) {
      final PageableVariable variable = PAGEABLE_VARIABLE.get();
      final Object parameter = variable.getParameter();
      final MappedStatement ms = variable.getMappedStatement();
      final BoundSql boundSql = ((StatementHandler) invocation.getTarget()).getBoundSql();

      final Connection connection = (Connection) invocation.getArgs()[0];

      final String countingSql = String.format("SELECT count(1) FROM ( %s )", boundSql.getSql());
      try (final PreparedStatement ps = connection.prepareStatement(countingSql)) {
        new DefaultParameterHandler(ms, parameter, boundSql).setParameters(ps);

        final ResultSet rs = ps.executeQuery();
        if (rs.next()) {
          variable.setTotal(rs.getLong(1));
        }
      }

      final String sql = String.format("%s LIMIT %d OFFSET (%d * %d)",
          boundSql.getSql(),
          variable.getPageSize(),
          variable.getPageSize(),
          variable.getPageNumber()
      );
      ReflectionUtils.setField(BoundSql.class.getDeclaredField("sql"), boundSql, sql);
    }

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
