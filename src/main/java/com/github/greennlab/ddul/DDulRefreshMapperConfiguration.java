package com.github.greennlab.ddul;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.reflections.Reflections;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.util.FieldUtils;

@Profile('!' + Application.PRODUCTION)
@org.springframework.context.annotation.Configuration
@RequiredArgsConstructor
@Slf4j
public class DDulRefreshMapperConfiguration implements InitializingBean, DisposableBean {

  private static final ExecutorService refreshMapperService = Executors.newSingleThreadExecutor();

  private final DDulMybatisConfiguration ddulMybatisConfiguration;
  private final SqlSessionFactory sqlSessionFactory;


  @Override
  public void destroy() throws InterruptedException {
    refreshMapperService.shutdown();

    int retry = 0;
    while (!refreshMapperService.awaitTermination(1, TimeUnit.SECONDS)) {
      TimeUnit.SECONDS.sleep(1);

      logger.info("try destroy to mapper refresh service!");
      if (++retry > 5) {
        System.exit(-1);
      }
    }

    logger.info("mapper refresh service destroyed!");
  }

  @Override
  public void afterPropertiesSet() throws IOException, URISyntaxException {
    final Set<MapperBundle> mapperBundles = new HashSet<>();

    for (String basePackage : ddulMybatisConfiguration.getBasePackages()) {
      final Set<Class<?>> mappers = new Reflections(basePackage)
          .getTypesAnnotatedWith(Mapper.class);

      for (Class<?> mapper : mappers) {
        final URL resource = mapper
            .getResource(
                String.format("/%s.xml", mapper.getName().replace('.', '/'))
            );

        logger.debug("watching to mapper XML {}", resource);

        if (null != resource) {
          mapperBundles.add(new MapperBundle(mapper, resource));
        }
      }
    }

    refreshMapperService
        .execute(new RefreshMapper(mapperBundles, sqlSessionFactory.getConfiguration()));
  }


  @EqualsAndHashCode(exclude = {"xml", "lastModified"})
  @Getter
  static class MapperBundle {

    private final Class<?> mapper;
    private final URL xml;

    @Setter
    private long lastModified;


    MapperBundle(Class<?> mapper, URL xml) {
      this.mapper = mapper;
      this.xml = xml;
      this.lastModified = getXmlLastModified();
    }

    public boolean isModified() {
      return this.lastModified != getXmlLastModified();
    }

    public void resetLastModified() {
      this.lastModified = getXmlLastModified();
    }


    private long getXmlLastModified() {
      try {
        switch (xml.getProtocol()) {
          case "jar":
            return xml.openConnection().getLastModified();
          case "file":
            return Files.getLastModifiedTime(Paths.get(xml.toURI())).toMillis();
          default:
            break;
        }
      } catch (IOException | URISyntaxException e) {
        logger.warn(e.getMessage());
      }

      return 0L;
    }
  }

  @RequiredArgsConstructor
  static class RefreshMapper implements Runnable {

    private final Set<MapperBundle> bundles;
    private final Configuration configuration;


    @Override
    public void run() {
      while (!refreshMapperService.isShutdown() && !refreshMapperService.isTerminated()) {
        final Iterator<MapperBundle> iterator = bundles.iterator();

        while (iterator.hasNext()) {
          refresh(iterator.next());
        }

        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }

    private void refresh(MapperBundle bundle) {
      if (bundle.isModified()) {
        final Class<?> mapper = bundle.getMapper();

        cleanupPreviousMapper(mapper);
        configuration.addMapper(mapper);

        bundle.resetLastModified();
        logger.info("changed mapper: {}", bundle.getXml());
      }
    }

    @SuppressWarnings("unchecked")
    private void cleanupPreviousMapper(Class<?> mapper) {
      ((Map<?, ?>)
          FieldUtils.getProtectedFieldValue("knownMappers",
              configuration.getMapperRegistry())
      ).remove(mapper);

      final String mapperClassName = mapper.getName();
      final String mapperXMLResource = String
          .format("%s.xml", mapperClassName.replace('.', '/'));

      ((Set<String>)
          FieldUtils.getProtectedFieldValue("loadedResources", configuration)
      ).removeAll(Arrays.asList(mapper.toString(), mapperXMLResource));

      ((Map<String, MappedStatement>)
          FieldUtils.getProtectedFieldValue("mappedStatements", configuration)
      ).entrySet()
          .removeIf(entry -> {
            try {
              return mapperXMLResource.equals(entry.getValue().getResource());
            } catch (ClassCastException e) {
              // empty
            }

            return false;
          });

      ((Map<String, KeyGenerator>)
          FieldUtils.getProtectedFieldValue("keyGenerators", configuration)
      ).entrySet()
          .removeIf(entry -> entry.getKey().startsWith(mapperClassName));
    }
  }
}
