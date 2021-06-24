package ddul.infrastructure.config;

import ddul.infrastructure.mybatis.PageableInterceptor;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MybatisConfiguration {

  @Bean
  MybatisRefreshableSqlSessionFactoryBean sqlSessionFactoryBean(
      final DataSource dataSource,
      final MybatisProperties properties
  ) {
    final MybatisRefreshableSqlSessionFactoryBean bean = new MybatisRefreshableSqlSessionFactoryBean();
    bean.setDataSource(dataSource);
    bean.setPlugins(
        new PageableInterceptor()
    );

    return bean;
  }

  @Bean
  @Primary
  SqlSessionFactory sqlSessionFactory(
      final MybatisRefreshableSqlSessionFactoryBean bean
  ) {
    final SqlSessionFactory factory = bean.getObject();
    if (factory != null) {
      factory.getConfiguration().setMapUnderscoreToCamelCase(true);
    }

    return factory;

  }

}
