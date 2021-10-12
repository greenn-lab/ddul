package io.github.greennlab.ddul.infrastructure.article.repository;

import io.github.greennlab.ddul.entity.AFewRepository;
import io.github.greennlab.ddul.infrastructure.article.ArticleComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DDulArticleCommentRepository extends AFewRepository<ArticleComment> {

  Page<ArticleComment> findAllByArticleId(Long articleId, Pageable pageable);

  @Modifying
  @Query("UPDATE ArticleComment a SET a.bunch = :id WHERE a.id = :id")
  void updateBunch(Long id);

  @Modifying
  @Query("UPDATE ArticleComment a SET a.sequel = a.sequel + 1 WHERE a.bunch = :bunch AND a.sequel >= :sequel")
  void shoveUpReplyOrders(Long bunch, Integer sequel);

}
