package io.github.greennlab.ddul.infrastructure.article.controller;

import io.github.greennlab.ddul.infrastructure.article.Article;
import io.github.greennlab.ddul.infrastructure.article.dto.ArticleInputDTO;
import io.github.greennlab.ddul.infrastructure.article.service.ArticleService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("DDulArticleController")
@RequestMapping("_article")
@RequiredArgsConstructor
public class ArticleController {

  private final ArticleService service;


  @GetMapping("list/{category}")
  public Page<Article> paginate(
      @Parameter(name = "분류", description = "공지사항, 자료실 등등.")
      @PathVariable String category,
      @RequestParam(required = false) String searchType,
      @RequestParam(required = false) String keyword,
      Pageable pageable) {
    return service.paginate(pageable, category, searchType, keyword);
  }

  @GetMapping("{id}")
  public Article paginate(@PathVariable Long id) {
    return service.select(id);
  }


  @PostMapping
  @PutMapping
  public Article save(@Validated @RequestBody ArticleInputDTO input) {
    final Article article = ArticleInputDTO.mapped.by(input);

    return service.insert(article);
  }


  @PutMapping
  public Article edit(@Validated @RequestBody Article article) {
    return service.update(article);
  }

  @DeleteMapping
  public void remove(Long id) {
    service.delete(id);
  }

}
