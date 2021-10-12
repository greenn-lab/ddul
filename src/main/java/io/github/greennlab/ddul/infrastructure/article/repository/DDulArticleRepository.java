package io.github.greennlab.ddul.infrastructure.article.repository;

import io.github.greennlab.ddul.infrastructure.article.Article;
import io.github.greennlab.ddul.entity.AFewRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DDulArticleRepository
    extends AFewRepository<Article>, DDulArticleQuerydslRepository {

  @Modifying
  @Query("UPDATE Article a SET a.bunch = :id WHERE a.id = :id")
  void updateBunch(Long id);

  @Modifying
  @Query("UPDATE Article a SET a.sequel = a.sequel + 1 WHERE a.bunch = :bunch AND a.sequel >= :sequel")
  void shoveUpReplyOrders(Long bunch, Integer sequel);

}
