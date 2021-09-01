package io.github.greennlab.ddul.article.repository;

import io.github.greennlab.ddul.article.ArticleCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface DDulArticleCategoryRepository
    extends Repository<ArticleCategory, String> {

  Page<ArticleCategory> findAllByRemoval(boolean removal, Pageable pageable);

  ArticleCategory findByCategory(String category);

  void save(ArticleCategory articleCategory);

}
