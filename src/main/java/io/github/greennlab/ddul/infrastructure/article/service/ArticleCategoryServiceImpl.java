package io.github.greennlab.ddul.infrastructure.article.service;

import static io.github.greennlab.ddul.DDulCacheConfiguration.CACHED_ARTICLE_CATEGORY;

import io.github.greennlab.ddul.infrastructure.article.ArticleCategory;
import io.github.greennlab.ddul.infrastructure.article.repository.DDulArticleCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("DDulArticleCategoryService")
@RequiredArgsConstructor
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

  private final DDulArticleCategoryRepository repository;


  @Override
  public Page<ArticleCategory> getPage(Pageable pageable) {
    return repository.findAllByRemoval(false, pageable);
  }

  @Cacheable(cacheNames = CACHED_ARTICLE_CATEGORY, key = "#category")
  @Override
  public ArticleCategory getArticleCategory(String category) {
    return repository.findByCategory(category);
  }

  @CacheEvict(cacheNames = CACHED_ARTICLE_CATEGORY, key = "#articleCategory.category")
  @Override
  public void save(ArticleCategory articleCategory) {
    repository.save(articleCategory);
  }

}
