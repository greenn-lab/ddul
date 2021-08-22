package com.github.greennlab.ddul.article.service;

import static com.github.greennlab.ddul.DDulCacheConfiguration.ARTICLE_CATEGORY;

import com.github.greennlab.ddul.article.ArticleCategory;
import com.github.greennlab.ddul.article.repository.ArticleCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

  private final ArticleCategoryRepository repository;

  @Cacheable(cacheNames = ARTICLE_CATEGORY, key = "#category")
  @Override
  public ArticleCategory getArticleCategory(String category) {
    return repository.findByCategory(category);
  }

}
