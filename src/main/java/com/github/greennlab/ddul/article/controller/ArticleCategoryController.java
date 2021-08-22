package com.github.greennlab.ddul.article.controller;

import com.github.greennlab.ddul.article.ArticleCategory;
import com.github.greennlab.ddul.article.service.ArticleCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("article-category")
@RequiredArgsConstructor
public class ArticleCategoryController {

  private final ArticleCategoryService service;


  @GetMapping("{category}")
  public ArticleCategory getArticleCategory(@PathVariable String category) {
    return service.getArticleCategory(category.toUpperCase());
  }

}
