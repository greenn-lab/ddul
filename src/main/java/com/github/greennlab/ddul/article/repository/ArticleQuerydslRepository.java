package com.github.greennlab.ddul.article.repository;

import com.github.greennlab.ddul.article.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleQuerydslRepository {

  Page<Article> findAllBy(String category, String searchType, String keyword, Pageable pageable);

}
