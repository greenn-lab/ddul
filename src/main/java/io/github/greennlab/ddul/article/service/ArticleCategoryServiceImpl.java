package io.github.greennlab.ddul.article.service;

import io.github.greennlab.ddul.article.ArticleCategory;
import io.github.greennlab.ddul.article.repository.ArticleCategoryRepository;
import io.github.greennlab.ddul.DDulCacheConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

  private final ArticleCategoryRepository repository;


  @Override
  public Page<ArticleCategory> getPage(Pageable pageable) {
    return repository.findAllByRemoval(false, pageable);
  }

  @Cacheable(cacheNames = DDulCacheConfiguration.ARTICLE_CATEGORY, key = "#category")
  @Override
  public ArticleCategory getArticleCategory(String category) {
    return repository.findByCategory(category);
  }

  @CacheEvict(cacheNames = DDulCacheConfiguration.ARTICLE_CATEGORY, key = "#articleCategory.category")
  @Override
  public void save(ArticleCategory articleCategory) {
    repository.save(articleCategory);
  }

}
