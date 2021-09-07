package io.github.greennlab.ddul.infrastructure.article.repository;

import static io.github.greennlab.ddul.infrastructure.article.QArticle.article;
import static io.github.greennlab.ddul.infrastructure.user.QUser.user;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.greennlab.ddul.infrastructure.article.Article;
import io.github.greennlab.ddul.infrastructure.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
public class DDulArticleQuerydslRepositoryImpl implements DDulArticleQuerydslRepository {

  public static final QBean<Article> columnsOfPage = Projections.bean(Article.class,
      article.id,
      article.bunch,
      article.depth,
      article.sequel,
      Projections.bean(
              User.class,
              user.id,
              user.username
          )
          .as("user"),
      article.author,
      article.email,
      article.password,
      article.title,
      article.read,
      article.created
  );


  private final JPAQueryFactory queryFactory;


  @Override
  public Page<Article> findAllBy(String category, String searchType, String keyword,
      Pageable pageable) {
    final QueryResults<Article> query = queryFactory
        .select(columnsOfPage)
        .from(article)
        .where(
            article.removal.eq(false)
                .and(article.category.eq(category))
                .and(conditionOfPage(searchType, keyword))
        )
        .orderBy(
            article.bunch.desc(),
            article.sequel.asc()
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetchResults();

    return new PageImpl<>(query.getResults(), pageable, query.getTotal());
  }

  private BooleanExpression conditionOfPage(String searchType, String keyword) {
    if (ObjectUtils.isEmpty(keyword)) {
      return null;
    }

    if ("1".equals(searchType)) {
      return article.title.contains(keyword);
    } else if ("2".equals(searchType)) {
      return article.fulltext.contains(keyword);
    } else if ("3".equals(searchType)) {
      return article.author.contains(keyword);
    } else {
      return article.author.contains(keyword)
          .or(article.title.contains(keyword))
          .or(article.fulltext.contains(keyword));
    }
  }

}
