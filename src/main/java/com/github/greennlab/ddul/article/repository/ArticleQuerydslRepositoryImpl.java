package com.github.greennlab.ddul.article.repository;

import static com.github.greennlab.ddul.article.QArticle.article;

import com.github.greennlab.ddul.article.Article;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ArticleQuerydslRepositoryImpl implements ArticleQuerydslRepository {

  public static final ConstructorExpression<Article> columns = Projections.constructor(
      Article.class,
      article.user,
      article.author,
      article.email,
      article.password,
      article.title,
      article.read
  );


  private final JPAQueryFactory queryFactory;


  @Override
  public Page<Article> pageBy(String searchType, String keyword, Pageable pageable) {
    final QueryResults<Article> results = queryFactory
        .select(columns)
        .from(article)
        .orderBy(
            article.bid.desc(),
            article.order.asc()
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetchResults();

    return new PageImpl<>(results.getResults(), pageable, results.getTotal());
  }

}
