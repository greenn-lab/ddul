package com.github.greennlab.ddul;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.reflections.Reflections;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.xml.XmlReaderContext;
import org.springframework.context.annotation.Profile;
import org.springframework.security.util.FieldUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.helpers.DefaultHandler;

@Profile('!' + Application.PRODUCTION)
@org.springframework.context.annotation.Configuration
@RequiredArgsConstructor
@Slf4j
public class DDulMybatisRefreshableMapperConfiguration implements InitializingBean, DisposableBean {

  private final ExecutorService mapperRefreshService = Executors.newSingleThreadExecutor();

  private WatchService mapperXMLWatchService;

  private final DDulMybatisConfiguration ddulMybatisConfiguration;
  private final SqlSessionFactory sqlSessionFactory;

  @Override
  public void destroy() throws Exception {
    mapperRefreshService.shutdown();

    if (!mapperRefreshService.isTerminated()) {
      int retry = 0;
      while (mapperRefreshService.awaitTermination(1, TimeUnit.SECONDS)) {
        logger.error("retry termination mapper refresh service!");
        if (++retry > 5) {
          System.exit(-1);
        }
      }
    }
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

        if (null != resource) {
          final URI uri = resource.toURI();

          if ("file".equals(uri.getScheme())) {
            final Path dir = Paths.get(uri).getParent();
            mapperDirectories.add(dir);
          }
        }
      }
    }

    mapperRefreshService.execute(
        new RefreshMapperXML(mapperDirectories, sqlSessionFactory.getConfiguration())
    );
  }


  private static class RefreshMapperXML implements Runnable {

    private final Set<Path> mapperDirectories;
    private final Configuration configuration;

    public RefreshMapperXML(Set<Path> mapperDirectories, Configuration configuration) {
      this.mapperDirectories = mapperDirectories;
      this.configuration = configuration;
    }

    @Override
    public void run() {
      try (
          final WatchService watchService = FileSystems.getDefault().newWatchService();
      ) {
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
            final Path file = (Path) modified.context();
            final File mapperXml = dir.resolve(file).toFile();

            logger.info("{}", mapperXml);
          });

//            final Class<?> mapper = mapperDirectories.get(file.toString());
//
//            if (null != mapper) {
//              logger.info("detect changed mapper xml {}!", file);
//
//              cleanupPreviousMapper(mapper);
//
//              configuration.addMapper(mapper);
//            }

          key.reset();
        }

      } catch (IOException e) {
        logger.error("mapper watch service stopped!", e);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        logger.error("mapper watch service stopped!", e);
      }
    }

    private void refreshMapper(Path mapperXmlPath) {

    }

    private String getNamespace(Path xml)
        throws ParserConfigurationException, SAXException {
      final AtomicReference<String> namespace = new AtomicReference<>("");

      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setFeature("http://mybatis.org/dtd/mybatis-3-mapper.dtd", true);

      SAXParserFactory saxFactory = SAXParserFactory.newInstance();

      try {


        final SAXParser parser = saxFactory.newSAXParser();

      SAXParser saxParser = saxFactory.newSAXParser();
      saxParser.parse(Files.newInputStream(xml), new DefaultHandler() {
          @Override
          public void startElement(String uri, String localName, String qName,
              Attributes attributes)
              throws SAXException {
            if ("mapper".equals(qName)) {
              namespace.set(attributes.getValue("namespace"));
              throw new SAXException("Breaking");
            }
          }
        });
      } catch (IOException | ParserConfigurationException e) {
        logger.error("Fail to get namespace of mapper!", e);
      } catch (SAXException e) {
        if (!"Breaking".equals(e.getMessage())) {
          logger.error("Fail to get namespace of mapper!", e);
        }
      }

      return namespace.get();
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
