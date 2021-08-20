package com.github.greennlab.ddul.article.repository;

import com.github.greennlab.ddul.article.ArticleCategory;
import org.springframework.data.repository.Repository;

public interface ArticleCategoryRepository
    extends Repository<ArticleCategory, String> {

  void save(ArticleCategory articleCategory);

}
