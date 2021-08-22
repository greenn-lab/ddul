package com.github.greennlab.ddul.article.service;

import com.github.greennlab.ddul.article.ArticleCategory;
import org.springframework.cache.annotation.Cacheable;

public interface ArticleCategoryService {

  @Cacheable()
  ArticleCategory getArticleCategory(String category);

}
