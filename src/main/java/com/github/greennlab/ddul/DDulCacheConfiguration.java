package com.github.greennlab.ddul;

import java.util.Collections;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
public class DDulCacheConfiguration {

  @Bean
  public CacheManager cacheManager() {
    final CompositeCacheManager managers = new CompositeCacheManager();
    managers.setCacheManagers(
        Collections.singletonList(
            new ConcurrentMapCacheManager("article-category")
        )
    );

    return managers;
  }

  @Scheduled(fixedDelay = 60 * 3 * 1000)
  public void clearAllCaches() {
    final CacheManager manager = cacheManager();

    for (String key : manager.getCacheNames()) {
      final Cache cache = manager.getCache(key);
      if (null != cache) {
        cache.clear();
      }
    }
  }

}
