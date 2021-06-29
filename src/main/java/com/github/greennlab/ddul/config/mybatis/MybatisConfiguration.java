package com.github.greennlab.ddul.config.mybatis;

import static org.springframework.context.ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS;

import com.github.greennlab.ddul.Application;
import com.github.greennlab.ddul.entity.Mappable;
import com.github.greennlab.ddul.mybatis.MapperType;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.StringUtils;

@Configuration
@EnableConfigurationProperties(MybatisProperties.class)
@AutoConfigureBefore(MybatisAutoConfiguration.class)
@RequiredArgsConstructor
@Slf4j
public class MybatisConfiguration {

  private final BeanFactory beanFactory;
  private final MybatisProperties properties;


  @PostConstruct
  public void setup() throws IOException, ClassNotFoundException {
    final Set<String> basePackages = getBasePackages();

    properties.setTypeAliasesPackage(String.join(",", basePackages));
    properties.setTypeAliasesSuperType(Mappable.class);
    properties.setConfiguration(configuration(basePackages));
  }

  private Set<String> getBasePackages() {
    final Set<String> basePackages = new HashSet<>();
    basePackages.add(Application.class.getPackage().getName());
    basePackages.addAll(AutoConfigurationPackages.get(beanFactory));

    final String typeAliasesPackage = properties.getTypeAliasesPackage();
    Arrays.stream(StringUtils.tokenizeToStringArray(typeAliasesPackage, CONFIG_LOCATION_DELIMITERS))
        .filter(StringUtils::hasText)
        .forEach(basePackages::add);

    return basePackages;
  }

  private org.apache.ibatis.session.Configuration configuration(Set<String> basePackages)
      throws IOException, ClassNotFoundException {

    final org.apache.ibatis.session.Configuration configuration
        = new org.apache.ibatis.session.Configuration();

    registryAlias(basePackages, configuration.getTypeAliasRegistry());
    configuration.setJdbcTypeForNull(JdbcType.VARCHAR);
    configuration.setMapUnderscoreToCamelCase(true);

    return configuration;
  }

  private void registryAlias(Set<String> basePackages, TypeAliasRegistry registry)
      throws IOException, ClassNotFoundException {
    final ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
    final MetadataReaderFactory metadataFactory = new CachingMetadataReaderFactory();

    for (final String basePackage : basePackages) {
      final Resource[] resources = patternResolver.getResources(
          String.format("classpath*:%s/**/*.class", basePackage.replace('.', '/'))
      );

      for (Resource classResource : resources) {
        final Class<?> clazz = getClassByResource(metadataFactory, classResource);
        registryAliasByMapperType(registry, clazz);
      }
    }
  }

  private Class<?> getClassByResource(MetadataReaderFactory metadataFactory, Resource classResource)
      throws IOException, ClassNotFoundException {
    final MetadataReader metadataReader = metadataFactory.getMetadataReader(classResource);
    final String className = metadataReader.getClassMetadata().getClassName();
    return Resources.classForName(className);
  }

  private void registryAliasByMapperType(TypeAliasRegistry registry, Class<?> clazz) {
    final MapperType mapperType = AnnotationUtils.findAnnotation(clazz, MapperType.class);

    if (null != mapperType) {
      final String alias = StringUtils.hasText(mapperType.value())
          ? mapperType.value()
          : clazz.getSimpleName();

      if (!registry.getTypeAliases().containsKey(alias)) {
        registry.registerAlias(alias, clazz);
      }
    }
  }
}
