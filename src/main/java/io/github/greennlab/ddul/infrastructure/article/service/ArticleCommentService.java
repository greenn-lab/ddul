package io.github.greennlab.ddul.infrastructure.article.service;

import io.github.greennlab.ddul.infrastructure.article.ArticleComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleCommentService {

  Page<ArticleComment> paginate(Long articleId, Pageable pageable);

  ArticleComment select(Long id);

  ArticleComment insert(ArticleComment comment);

  ArticleComment update(ArticleComment comment);

  void delete(Long id);

}
