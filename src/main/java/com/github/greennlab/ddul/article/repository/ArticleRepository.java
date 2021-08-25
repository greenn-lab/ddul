package com.github.greennlab.ddul.article.repository;

import com.github.greennlab.ddul.article.Article;
import com.github.greennlab.ddul.entity.AFewRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends AFewRepository<Article>, ArticleQuerydslRepository {

  @Modifying
  @Query("UPDATE Article a SET a.removal = true WHERE a.id = :id")
  void deleteById(Long id);

  @Modifying
  @Query("UPDATE Article a SET a.order = a.order + 1 WHERE a.bid = :bid AND a.order >= :order ")
  void updateRepliesOrder(Long bid, Integer order);

  @Modifying
  @Query("UPDATE Article a SET a.bid = :id WHERE a.id = :id")
  void updateBid(Long id);
}
