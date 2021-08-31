package io.github.greennlab.ddul.article.repository;

import io.github.greennlab.ddul.article.Article;
import io.github.greennlab.ddul.article.dto.ArticleOutputDTO;
import io.github.greennlab.ddul.user.User;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.greennlab.ddul.article.QArticle;
import io.github.greennlab.ddul.user.QUser;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
public class ArticleQuerydslRepositoryImpl implements ArticleQuerydslRepository {

  public static final QBean<Article> columnsOfPage = Projections.bean(Article.class,
      QArticle.article.id,
      QArticle.article.depth,
      Projections.bean(
              User.class,
              QUser.user.id,
              QUser.user.username
          )
          .as("user"),
      QArticle.article.author,
      QArticle.article.email,
      QArticle.article.password,
      QArticle.article.title,
      QArticle.article.read,
      QArticle.article.created
  );


  private final JPAQueryFactory queryFactory;


  @Override
  public Page<ArticleOutputDTO> findAllBy(String category, String searchType, String keyword,
      Pageable pageable) {
    final QueryResults<Article> query = queryFactory
        .select(columnsOfPage)
        .from(QArticle.article)
        .where(
            QArticle.article.removal.eq(false)
                .and(QArticle.article.category.eq(category))
                .and(conditionOfPage(searchType, keyword))
        )
        .orderBy(
            QArticle.article.bid.desc(),
            QArticle.article.order.asc()
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetchResults();

    final List<ArticleOutputDTO> list = query.getResults().stream()
        .map(ArticleOutputDTO.mapped::to).collect(Collectors.toList());

    return new PageImpl<>(list, pageable, query.getTotal());
  }

  private BooleanExpression conditionOfPage(String searchType, String keyword) {
    if (ObjectUtils.isEmpty(keyword)) {
      return null;
    }

    if ("1".equals(searchType)) {
      return QArticle.article.title.contains(keyword);
    } else if ("2".equals(searchType)) {
      return QArticle.article.fulltext.contains(keyword);
    } else if ("3".equals(searchType)) {
      return QArticle.article.author.contains(keyword);
    } else {
      return QArticle.article.author.contains(keyword)
          .or(QArticle.article.title.contains(keyword))
          .or(QArticle.article.fulltext.contains(keyword));
    }
  }

}
