package io.github.greennlab.ddul.infrastructure.article.controller;

import io.github.greennlab.ddul.infrastructure.article.ArticleCategory;
import io.github.greennlab.ddul.infrastructure.article.service.ArticleCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("article-config")
@RequiredArgsConstructor
public class ArticleCategoryController {

  private final ArticleCategoryService service;


  @GetMapping("{category}")
  public ArticleCategory getArticleCategory(@PathVariable String category) {
    return service.getArticleCategory(category);
  }

}
