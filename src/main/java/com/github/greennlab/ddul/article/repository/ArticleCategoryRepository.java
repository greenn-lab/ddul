package com.github.greennlab.ddul.article.repository;

import com.github.greennlab.ddul.article.ArticleCategory;
import org.springframework.data.repository.Repository;

public interface ArticleCategoryRepository
    extends Repository<ArticleCategory, String> {

  ArticleCategory findByCategory(String category);

  void save(ArticleCategory articleCategory);

}
