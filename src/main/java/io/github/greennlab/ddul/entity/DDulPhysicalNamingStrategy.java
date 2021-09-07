package io.github.greennlab.ddul.entity;

import io.github.greennlab.ddul.DDulProperties;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DDulPhysicalNamingStrategy extends SpringPhysicalNamingStrategy {

  private final DDulProperties properties;


  @Override
  public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
    final Identifier named = super.toPhysicalTableName(name, jdbcEnvironment);
    final String tableName = properties.getTablePrefix() + named.getText();

    return new Identifier(tableName, named.isQuoted());
  }
}
