package io.github.greennlab.ddul.infrastructure.article.controller;

import io.github.greennlab.ddul.infrastructure.article.Article;
import io.github.greennlab.ddul.infrastructure.article.dto.ArticleInputDTO;
import io.github.greennlab.ddul.infrastructure.article.dto.ArticleInputDTO.EDIT;
import io.github.greennlab.ddul.infrastructure.article.service.ArticleService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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


  @GetMapping("{category}/list")
  public Page<Article> paginate(
      @Parameter(name = "category", description = "notice(공지사항), faq, qna 등등.")
      @PathVariable String category,
      @Parameter(name = "검색항목") @RequestParam(required = false) String searchType,
      @Parameter(name = "검색어") @RequestParam(required = false) String keyword,
      @Parameter(name = "페이지", description = "page=페이지번호&size=목록당출력행수")
      @RequestParam(required = false) @PageableDefault Pageable pageable) {
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
  public Article edit(@Validated(EDIT.class) @RequestBody ArticleInputDTO article) {
    return service.update(ArticleInputDTO.mapped.by(article));
  }

  @DeleteMapping
  public void remove(Long id) {
    service.delete(id);
  }

}
