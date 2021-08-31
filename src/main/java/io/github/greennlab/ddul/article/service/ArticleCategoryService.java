package io.github.greennlab.ddul.article.service;

import io.github.greennlab.ddul.article.ArticleCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleCategoryService {

  ArticleCategory getArticleCategory(String category);

  Page<ArticleCategory> getPage(Pageable pageable);

  void save(ArticleCategory articleCategory);
}
