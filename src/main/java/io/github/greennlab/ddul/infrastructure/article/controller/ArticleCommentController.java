package io.github.greennlab.ddul.infrastructure.article.controller;

import static io.github.greennlab.ddul.infrastructure.article.dto.ArticleCommentDTO.mapped;

import io.github.greennlab.ddul.infrastructure.article.ArticleComment;
import io.github.greennlab.ddul.infrastructure.article.dto.ArticleCommentDTO;
import io.github.greennlab.ddul.infrastructure.article.dto.ArticleCommentDTO.EDIT;
import io.github.greennlab.ddul.infrastructure.article.service.ArticleCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${ddul.api.article:_article}/comment")
@Tag(name = "댓글", description = "comment api")
@RequiredArgsConstructor
public class ArticleCommentController {

  private final ArticleCommentService service;

  @GetMapping("{articleId}/list")
  @Operation(description = "게시글에 대한 댓글 목록")
  public Page<ArticleComment> comments(
      @Parameter(name = "articleId", description = "게시글 ID") @PathVariable Long articleId,
      @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
    return service.paginate(articleId, pageable);
  }

  @GetMapping("{id}")
  public ArticleCommentDTO comment(@PathVariable Long id) {
    return mapped.to(service.select(id));
  }

  @PostMapping
  public ArticleCommentDTO insert(@Validated @RequestBody ArticleCommentDTO comment) {
    return mapped.to(
        service.insert(mapped.by(comment))
    );
  }

  @PutMapping
  public ArticleCommentDTO update(@Validated(EDIT.class) @RequestBody ArticleCommentDTO comment) {
    return mapped.to(
        service.update(mapped.by(comment))
    );
  }

  @DeleteMapping("{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }

}
