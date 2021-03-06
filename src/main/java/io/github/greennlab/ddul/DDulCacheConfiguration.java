package io.github.greennlab.ddul;

import java.util.Arrays;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
public class DDulCacheConfiguration {

  public static final String CACHED_ARTICLE_CATEGORY = "ddul-article-category";
  public static final String CACHED_CODE = "ddul-code";


  @Bean
  public CacheManager cacheManager() {
    final CompositeCacheManager managers = new CompositeCacheManager();
    managers.setCacheManagers(
        Arrays.asList(
            new ConcurrentMapCacheManager(CACHED_ARTICLE_CATEGORY),
            new ConcurrentMapCacheManager(CACHED_CODE)
        )
    );

    return managers;
  }

  @Profile('!' + Application.PRODUCTION)
  @Scheduled(fixedDelay = 3 * 1000)
  public void developmentClearAllCaches() {
    clearAllCaches();
  }

  @Profile(Application.PRODUCTION)
  @Scheduled(fixedDelay = 60 * 3 * 1000)
  public void productionClearAllCaches() {
    clearAllCaches();
  }

  private void clearAllCaches() {
    final CacheManager manager = cacheManager();

    for (String key : manager.getCacheNames()) {
      final Cache cache = manager.getCache(key);
      if (null != cache) {
        cache.clear();
      }
    }
  }

}
