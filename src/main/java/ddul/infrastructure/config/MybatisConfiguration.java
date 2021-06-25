package ddul.infrastructure.config;

import ddul.infrastructure.mybatis.PageableExecuteInterceptor;
import ddul.infrastructure.mybatis.PageableBuildupInterceptor;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MybatisConfiguration {

  @Bean
  MybatisRefreshableSqlSessionFactoryBean sqlSessionFactoryBean(
      final DataSource dataSource,
      final MybatisProperties properties,
      final SqlSessionFactoryBean beforeBean
  ) {
    final MybatisRefreshableSqlSessionFactoryBean bean = new MybatisRefreshableSqlSessionFactoryBean();

    bean.setDataSource(dataSource);
    bean.setPlugins(
        new PageableBuildupInterceptor(),
        new PageableExecuteInterceptor()
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
