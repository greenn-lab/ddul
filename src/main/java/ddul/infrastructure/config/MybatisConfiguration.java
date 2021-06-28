package ddul.infrastructure.config;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import ddul.infrastructure.config.mybatis.PageableBuildupInterceptor;
import ddul.infrastructure.config.mybatis.PageableExecuteInterceptor;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class MybatisConfiguration {


  //  @Bean
  VendorDatabaseIdProvider databaseIdProvider() {
    VendorDatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
    Properties properties = new Properties();
    properties.put("SQL Server", "sqlserver");
    properties.put("DB2", "db2");
    properties.put("H2", "h2");
    properties.put("Oracle", "oracle");
    databaseIdProvider.setProperties(properties);

    return databaseIdProvider;
  }

  final SqlSessionFactory sqlSessionFactory;
  final MapperFactoryBean<?> mapperFactoryBean;

  @PostConstruct
  void watchModifiedXMLMapper() throws IOException, URISyntaxException {
    final org.apache.ibatis.session.Configuration configuration = sqlSessionFactory
        .getConfiguration();

    configuration.addInterceptor(new PageableBuildupInterceptor());
    configuration.addInterceptor(new PageableExecuteInterceptor());

    final WatchService watchService = FileSystems.getDefault().newWatchService();
    {

      final Path root = Paths.get(this.getClass().getResource("/").toURI());
      root.register(watchService, ENTRY_MODIFY);

      final ExecutorService executorService = Executors.newSingleThreadExecutor();
      executorService.execute(() -> {

        logger.info("file watcher at {}", root);

        boolean poll = true;
        while (poll) {
          try {
            WatchKey take = watchService.take();

            take.pollEvents().forEach(modified -> {
              logger.info("{}", modified.context());
            });

            poll = take.reset();
          } catch (Throwable e) {
            e.printStackTrace();
          }
        }
      });

    }



    /*
    final MapperRegistry mapperRegistry = configuration.getMapperRegistry();
    for (Class<?> mapper : mapperRegistry.getMappers()) {
      final String xmlResource = String.format("/%s.xml", mapper.getName().replace('.', '/'));
      final URL urlResource = mapper.getResource(xmlResource);

      final Path mapperPath = Paths.get(urlResource.toURI());
      mapperPath.register(watchService, ENTRY_MODIFY).pollEvents().forEach(
          action -> {
            try {
              logger.info("{} ({})", mapperPath, Files.getLastModifiedTime(mapperPath));
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
      );

      logger.info("{} class -> {}", mapper, urlResource);
    }
    */
  }
}
