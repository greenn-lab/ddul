package io.github.greennlab.ddul.infrastructure.article.repository;

import io.github.greennlab.ddul.infrastructure.article.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DDulArticleQuerydslRepository {

  Page<Article> findAllBy(String category, String searchType, String keyword, Pageable pageable);

}
