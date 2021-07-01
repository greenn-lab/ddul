package com.github.greennlab.ddul;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
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
public class MybatisRefreshableMapperConfiguration implements InitializingBean, DisposableBean {

  private static final WatchService mapperXMLWatchService;

  static {
    WatchService watchService;
    try {
      watchService = FileSystems.getDefault().newWatchService();
    } catch (IOException e) {
      watchService = null;
    }

    mapperXMLWatchService = watchService;
  }

  private final MybatisConfiguration mybatisConfiguration;
  private final SqlSessionFactory sqlSessionFactory;

  @Override
  public void destroy() throws Exception {
    mapperXMLWatchService.close();
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    final Map<String, Class<?>> pathWithMapper = new HashMap<>();

    for (String basePackage : mybatisConfiguration.getBasePackages()) {
      final Set<Class<?>> mappers = new Reflections(basePackage)
          .getTypesAnnotatedWith(Mapper.class);

      for (Class<?> mapper : mappers) {
        final URL resource = mapper
            .getResource(
                String.format("/%s.xml", mapper.getName().replace('.', '/'))
            );

        logger.info("watching to mapper XML {}", resource);

        if (null != resource) {
          final URI uri = resource.toURI();

          if ("file".equals(uri.getScheme())) {
            final Path self = Paths.get(uri);

            pathWithMapper.put(self.getFileName().toString(), mapper);
            self.getParent().register(mapperXMLWatchService, ENTRY_MODIFY);
          }
        }
      }
    }

    final ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.execute(
        new RefreshMapperXML(pathWithMapper, sqlSessionFactory.getConfiguration())
    );

  }


  private static class RefreshMapperXML implements Runnable {

    private final Map<String, Class<?>> pathWithMapper;
    private final Configuration configuration;

    public RefreshMapperXML(Map<String, Class<?>> pathWithMapper, Configuration configuration) {
      this.pathWithMapper = pathWithMapper;
      this.configuration = configuration;
    }

    @Override
    public void run() {
      boolean poll = true;
      while (poll) {
        try {
          final WatchKey take = mapperXMLWatchService.take();

          /*
          https://stackoverflow.com/questions/16777869/java-7-watchservice-ignoring-multiple-occurrences-of-the-same-event

          //
          // Prevent receiving two separate ENTRY_MODIFY events: file modified
          // and timestamp updated. Instead, receive one ENTRY_MODIFY event
          // with two counts.
          */
          MILLISECONDS.sleep(50);

          take.pollEvents().forEach(modified -> {
            final Path file = (Path) modified.context();
            final Class<?> mapper = pathWithMapper.get(file.toString());

            if (null != mapper) {
              logger.info("detect changed mapper xml {}!", file);

              cleanupPreviousMapper(mapper);

              configuration.addMapper(mapper);
            }
          });

          poll = take.reset();
        } catch (InterruptedException | ClosedWatchServiceException e) {
          Thread.currentThread().interrupt();
        }
      }
    }

    @SuppressWarnings("unchecked")
    private void cleanupPreviousMapper(Class<?> mapper) {
      ((Map<?, ?>)
          FieldUtils.getProtectedFieldValue("knownMappers",
              configuration.getMapperRegistry())
      ).remove(mapper);

      final String mapperXMLResource = String
          .format("%s.xml", mapper.getName().replace('.', '/'));

      ((Set<String>)
          FieldUtils.getProtectedFieldValue("loadedResources", configuration)
      ).removeAll(Arrays.asList(mapper.toString(), mapperXMLResource));

      ((Map<String, MappedStatement>)
          FieldUtils.getProtectedFieldValue("mappedStatements", configuration)
      ).entrySet()
          .removeIf(entry -> mapperXMLResource.equals(entry.getValue().getResource()));
    }

  }
}
