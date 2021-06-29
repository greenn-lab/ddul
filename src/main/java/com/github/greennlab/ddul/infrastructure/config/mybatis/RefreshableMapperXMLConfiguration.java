package com.github.greennlab.ddul.infrastructure.config.mybatis;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

@Configuration
@AutoConfigureAfter(MybatisAutoConfiguration.class)
@RequiredArgsConstructor
@Slf4j
public class RefreshableMapperXMLConfiguration implements DisposableBean {

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


  final SqlSessionFactory sqlSessionFactory;
  final MapperFactoryBean<?> mapperFactoryBean;
  final Environment environment;


  @Override
  public void destroy() throws Exception {
    mapperXMLWatchService.close();
  }

  @PostConstruct
  void settings() throws IOException, URISyntaxException {
    final org.apache.ibatis.session.Configuration configuration
        = sqlSessionFactory.getConfiguration();

    configuration.addInterceptor(new PageableBuildupInterceptor());
    configuration.addInterceptor(new PageableExecuteInterceptor());

    if (!environment.acceptsProfiles(Profiles.of("development"))) {
      return;
    }

    final Map<String, Class<?>> pathWithMapper = new HashMap<>();

    final MapperRegistry mapperRegistry = configuration.getMapperRegistry();
    for (Class<?> mapper : mapperRegistry.getMappers()) {
      final URL resource = mapper
          .getResource(String.format("/%s.xml", mapper.getName().replace('.', '/')));

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

    final ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.execute(
        new RefreshMapperXML(pathWithMapper, mapperRegistry, configuration)
    );
  }


  @RequiredArgsConstructor
  private static class RefreshMapperXML implements Runnable {

    private final Map<String, Class<?>> pathWithMapper;
    private final MapperRegistry mapperRegistry;
    private final org.apache.ibatis.session.Configuration configuration;

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

              final MetaObject meta = SystemMetaObject.forObject(mapperRegistry);
              ((Map<?, ?>) meta.getValue("knownMappers")).remove(mapper);

              final MetaObject metaObject = SystemMetaObject.forObject(configuration);
              final String mapperXMLResource = String
                  .format("%s.xml", mapper.getName().replace('.', '/'));

              @SuppressWarnings("unchecked") final Set<String> loadedResources
                  = (Set<String>) metaObject.getValue("loadedResources");
              loadedResources.removeAll(Arrays.asList(mapper.toString(), mapperXMLResource));

              @SuppressWarnings("unchecked") final Collection<MappedStatement> mss
                  = (Collection<MappedStatement>) metaObject.getValue("mappedStatements");
              mss.removeIf(ms -> ms.getResource().equals(mapperXMLResource));

              configuration.addMapper(mapper);
            }
          });

          poll = take.reset();
        } catch (InterruptedException | ClosedWatchServiceException e) {
          Thread.currentThread().interrupt();
        }
      }
    }

  }
}