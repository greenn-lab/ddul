package io.github.greennlab.ddul.infrastructure.article.service;

import io.github.greennlab.ddul.infrastructure.article.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {

  Page<Article> paginate(Pageable pageable, String category, String searchType, String keyword);

  Article select(Long id);

  Article insert(Article article);

  Article update(Article article);

  void delete(Long id);

}
