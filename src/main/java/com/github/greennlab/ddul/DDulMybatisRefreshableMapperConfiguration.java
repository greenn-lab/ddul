package com.github.greennlab.ddul;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.reflections.Reflections;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.util.FieldUtils;
import org.springframework.util.StringUtils;

@Profile('!' + Application.PRODUCTION)
@org.springframework.context.annotation.Configuration
@RequiredArgsConstructor
@Slf4j
public class DDulMybatisRefreshableMapperConfiguration implements InitializingBean, DisposableBean {

  private final DDulMybatisConfiguration ddulMybatisConfiguration;
  private final SqlSessionFactory sqlSessionFactory;

  private Thread refreshMapperService = null;


  @Override
  public void destroy() throws Exception {
    if (refreshMapperService != null) {
      refreshMapperService.interrupt();
    }

    logger.info("mapper refresh service destroyed!");
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    final Set<Path> mapperDirectories = new HashSet<>();

    for (String basePackage : ddulMybatisConfiguration.getBasePackages()) {
      final Set<Class<?>> mappers = new Reflections(basePackage)
          .getTypesAnnotatedWith(Mapper.class);

      for (Class<?> mapper : mappers) {
        final URL resource = mapper
            .getResource(
                String.format("/%s.xml", mapper.getName().replace('.', '/'))
            );

        logger.info("watching to mapper XML {}", resource);

        if (null != resource && resource.getProtocol().startsWith("file")) {
          final URI uri = resource.toURI();

          if ("file".equals(uri.getScheme())) {
            final Path dir = Paths.get(uri).getParent();
            mapperDirectories.add(dir);
          }
        }
      }
    }

    final RefreshMapper refreshMapper = new RefreshMapper(mapperDirectories,
        sqlSessionFactory.getConfiguration());

    refreshMapperService = new Thread(refreshMapper, "refresh-mapper");
    refreshMapperService.start();
  }


  private static class RefreshMapper implements Runnable {

    private final Set<Path> mapperDirectories;
    private final Configuration configuration;

    private final WatchService watchService;


    public RefreshMapper(Set<Path> mapperDirectories, Configuration configuration)
        throws IOException {
      this.mapperDirectories = mapperDirectories;
      this.configuration = configuration;
      this.watchService = FileSystems.getDefault().newWatchService();
    }

    @Override
    public void run() {
      try {
        for (Path dir : mapperDirectories) {
          dir.register(watchService, ENTRY_MODIFY);
        }

        WatchKey key;
        while ((key = watchService.take()) != null) {
          /*
          https://stackoverflow.com/questions/16777869/java-7-watchservice-ignoring-multiple-occurrences-of-the-same-event
          //
          // Prevent receiving two separate ENTRY_MODIFY events: file modified
          // and timestamp updated. Instead, receive one ENTRY_MODIFY event
          // with two counts.
          */
          MILLISECONDS.sleep(50);

          final Path dir = (Path) key.watchable();
          key.pollEvents().forEach(modified -> {
            final Path context = (Path) modified.context();
            if (context.toString().toLowerCase().endsWith(".xml")) {
              final Path mapperXml = dir.resolve(context);
              refreshMapper(mapperXml);
            }
          });

          key.reset();
        }
      } catch (IOException | ClosedWatchServiceException e) {
        logger.error("stop mapper watch service with {}", e.getClass().toString());
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        logger.error("stop mapper watch service with {}", e.getClass().toString());
      } finally {
        close();
      }
    }

    public void close() {
      try {
        watchService.close();
      } catch (IOException e) {
        logger.error(e.getMessage(), e);
      }

      logger.info("mapper watcher finished!");
    }

    private void refreshMapper(Path mapperXmlPath) {
      try (
          final InputStream in = Files.newInputStream(mapperXmlPath);
      ) {
        final XPathParser parser = new XPathParser(in, true,
            configuration.getVariables(), new XMLMapperEntityResolver());

        final XNode xNode = parser.evalNode("/mapper");
        if (null != xNode) {
          final String namespace = xNode.getStringAttribute("namespace");
          if (StringUtils.hasText(namespace)) {
            final Class<?> mapper = Class.forName(namespace);

            cleanupPreviousMapper(mapper);

            configuration.addMapper(mapper);
            logger.info("detect changed mapper: {}", mapperXmlPath);
          }
        }
      } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
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
          .removeIf(entry -> {
            try {
              return mapperXMLResource.equals(entry.getValue().getResource());
            } catch (ClassCastException e) {
              // empty
            }

            return false;
          });
    }

  }
}
