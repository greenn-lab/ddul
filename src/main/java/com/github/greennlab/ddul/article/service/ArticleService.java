package com.github.greennlab.ddul.article.service;

import com.github.greennlab.ddul.article.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {

  Page<Article> pageBy(String category, String searchType, String keyword, Pageable pageable);

  Article select(Long id);

  Article insert(Article article);

  Article update(Article article);

  void delete(Long id);

}
