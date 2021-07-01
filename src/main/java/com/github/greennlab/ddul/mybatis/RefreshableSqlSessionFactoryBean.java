package com.github.greennlab.ddul.mybatis;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.io.Resource;

@Slf4j
@SuppressWarnings("unused")
public class RefreshableSqlSessionFactoryBean
    extends SqlSessionFactoryBean implements DisposableBean {

  private SqlSessionFactory proxy;

  private Timer timer;
  private TimerTask task;

  private Resource[] mapperLocations;

  private boolean running = false;

  private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
  private final Lock readLock = rwl.readLock();
  private final Lock writeLock = rwl.writeLock();

  @Override
  public void setMapperLocations(Resource[] mapperLocations) {
    super.setMapperLocations(mapperLocations);
    this.mapperLocations = mapperLocations;
  }

  public void refresh() {
    logger.info("loading sql mappers ...");

    writeLock.lock();
    try {
      super.afterPropertiesSet();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    } finally {
      writeLock.unlock();
    }
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    super.afterPropertiesSet();

    setRefreshable();
  }

  private void setRefreshable() {
    proxy = (SqlSessionFactory) Proxy.newProxyInstance(
        SqlSessionFactory.class.getClassLoader(),
        new Class[]{SqlSessionFactory.class},
        (proxied, method, args) -> method.invoke(getParentObject(), args));


    final Map<Resource, Long> map = new HashMap<>();
    task = new TimerTask() {

      public void run() {
        if (isModified()) {
          try {
            refresh();
          } catch (Exception e) {
            logger.error("caught exception", e);
          }
        }
      }

      private boolean isModified() {
        boolean retVal = false;

        if (mapperLocations != null) {
          for (Resource mappingLocation : mapperLocations) {
            retVal |= findModifiedResource(mappingLocation);
          }
        }

        return retVal;
      }

      private boolean findModifiedResource(Resource resource) {
        boolean retVal = false;
        List<String> modifiedResources = new ArrayList<>();

        try {
          long modified = resource.lastModified();

          if (map.containsKey(resource)) {
            long lastModified = map.get(resource);

            if (lastModified != modified) {
              map.put(resource, modified);
              modifiedResources.add(resource.getDescription());
              retVal = true;
            }
          } else {
            map.put(resource, modified);
          }
        } catch (IOException e) {
          logger.error("caught exception", e);
        }

        if (retVal) {
          logger.info("modified sql mapper {} ", modifiedResources);
        }

        return retVal;
      }
    };

    timer = new Timer(true);
    resetInterval();

  }

  private Object getParentObject() throws Exception {
    readLock.lock();
    try {
      return super.getObject();

    } finally {
      readLock.unlock();
    }
  }

  @Override
  public SqlSessionFactory getObject() {
    return this.proxy;
  }

  @Override
  public Class<? extends SqlSessionFactory> getObjectType() {
    return (this.proxy != null ? this.proxy.getClass()
        : SqlSessionFactory.class);
  }

  private void resetInterval() {
    if (running) {
      timer.cancel();
      running = false;
    }

    timer.schedule(task, 0, 1000);
    running = true;
  }

  public void destroy() {
    timer.cancel();
  }
}
