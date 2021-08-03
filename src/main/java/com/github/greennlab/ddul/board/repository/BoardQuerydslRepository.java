package com.github.greennlab.ddul.board.repository;

import com.github.greennlab.ddul.board.Board;
import com.github.greennlab.ddul.board.QBoard;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

@Repository
@RequiredArgsConstructor
public class BoardQuerydslRepository {

  private static final QBoard board = QBoard.board;


  private final JPAQueryFactory query;


  public Page<Board> findAll(String keyword, Pageable pageable) {
    final QueryResults<Board> results = query
        .selectFrom(board)
        .where(matches(keyword))
        .orderBy(board.id.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetchResults();

    return new PageImpl<>(results.getResults(), pageable, results.getTotal());
  }

  private BooleanExpression matches(String keyword) {
    if (ObjectUtils.isEmpty(keyword)) {
      return null;
    }

    return board.title.contains(keyword);
  }

}
