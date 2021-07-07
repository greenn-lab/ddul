package com.github.greennlab.ddul;

import static org.springframework.context.ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS;

import com.github.greennlab.ddul.mybatis.Mappable;
import com.github.greennlab.ddul.mybatis.MapperType;
import com.github.greennlab.ddul.mybatis.PageableBuildupInterceptor;
import com.github.greennlab.ddul.mybatis.PageableExecuteInterceptor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.reflections.Reflections;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

@org.springframework.context.annotation.Configuration
@RequiredArgsConstructor
@Slf4j
public class DDulMybatisConfiguration {

  private final BeanFactory beanFactory;
  private final MybatisProperties properties;


  @Bean
  ConfigurationCustomizer mybatisConfigurationCustomizer() {
    final Set<String> basePackages = getBasePackages();
    properties.setTypeAliasesPackage(String.join(",", basePackages));
    properties.setTypeAliasesSuperType(Mappable.class);

    final Configuration newConfiguration = new Configuration();
    properties.setConfiguration(newConfiguration);

    final TypeAliasRegistry typeAliasRegistry = newConfiguration.getTypeAliasRegistry();
    registryAlias(basePackages, typeAliasRegistry);

    return configuration -> {
      configuration.addInterceptor(new PageableBuildupInterceptor());
      configuration.addInterceptor(new PageableExecuteInterceptor());
      configuration.setJdbcTypeForNull(JdbcType.VARCHAR);
      configuration.setMapUnderscoreToCamelCase(true);
    };
  }

  Set<String> getBasePackages() {
    final Set<String> basePackages = new HashSet<>();
    basePackages.add(Application.class.getPackage().getName());
    basePackages.addAll(AutoConfigurationPackages.get(beanFactory));

    final String typeAliasesPackage = properties.getTypeAliasesPackage();
    Arrays.stream(StringUtils.tokenizeToStringArray(typeAliasesPackage, CONFIG_LOCATION_DELIMITERS))
        .filter(StringUtils::hasText)
        .forEach(basePackages::add);

    return basePackages;
  }

  private void registryAlias(Set<String> basePackages, TypeAliasRegistry registry) {
    for (final String basePackage : basePackages) {
      final Set<Class<?>> mappers = new Reflections(basePackage)
          .getTypesAnnotatedWith(MapperType.class);

      for (Class<?> mapper : mappers) {
        registryAliasByMapperType(mapper, registry);
      }
    }
  }

  private void registryAliasByMapperType(Class<?> mapper, TypeAliasRegistry registry) {
    final MapperType mapperType = mapper.getAnnotation(MapperType.class);

    final String alias = StringUtils.hasText(mapperType.value())
        ? mapperType.value()
        : mapper.getSimpleName();

    registry.registerAlias(alias, mapper);
  }

}
