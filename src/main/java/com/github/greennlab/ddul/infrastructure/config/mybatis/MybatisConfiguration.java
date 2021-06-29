package com.github.greennlab.ddul.infrastructure.config.mybatis;

import com.github.greennlab.ddul.infrastructure.entity.Mappable;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@EnableConfigurationProperties(MybatisProperties.class)
@AutoConfigureBefore(MybatisAutoConfiguration.class)
@RequiredArgsConstructor
@Slf4j
public class MybatisConfiguration {

  private final MybatisProperties properties;

  @PostConstruct
  public void setup() {
    final String prev = StringUtils.hasText(properties.getTypeAliasesPackage())
        ? properties.getTypeAliasesPackage() + ","
        : "";

    properties.setTypeAliasesPackage(prev + "com.github.greennlab.ddul");
    properties.setTypeAliasesSuperType(Mappable.class);
  }

}
