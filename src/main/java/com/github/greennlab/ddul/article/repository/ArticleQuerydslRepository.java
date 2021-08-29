package com.github.greennlab.ddul.article.repository;

import com.github.greennlab.ddul.article.dto.ArticleOutputDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleQuerydslRepository {

  Page<ArticleOutputDTO> findAllBy(String category, String searchType, String keyword, Pageable pageable);

}
