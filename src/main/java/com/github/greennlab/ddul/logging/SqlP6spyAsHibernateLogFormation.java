package com.github.greennlab.ddul.logging;

import com.github.greennlab.ddul.Application;
import com.p6spy.engine.spy.P6ModuleManager;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import javax.annotation.PostConstruct;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile('!' + Application.PRODUCTION)
@Configuration
public class SqlP6spyAsHibernateLogFormation implements MessageFormattingStrategy {

  @PostConstruct
  void setup() {
    P6ModuleManager.getInstance()
        .getOptions(P6SpyOptions.class)
        .setLogMessageFormat(SqlP6spyAsHibernateLogFormation.class.getName());
  }

  @Override
  public String formatMessage(int connectionId, String now, long elapsed, String category,
      String prepared, String sql, String url) {
    return FormatStyle.BASIC.getFormatter().format(sql)
        + String.format("%n%n\t-- elapsed %dms from connection %d", elapsed, connectionId);
  }

}
