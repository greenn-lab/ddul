package com.github.greennlab.ddul.article.service;

import com.github.greennlab.ddul.article.Article;
import com.github.greennlab.ddul.article.dto.ArticleOutputDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {

  Page<ArticleOutputDTO> pageBy(String category, String searchType, String keyword, Pageable pageable);

  ArticleOutputDTO select(Long id);

  ArticleOutputDTO insert(Article article);

  ArticleOutputDTO update(Article article);

  void delete(Long id);

}
