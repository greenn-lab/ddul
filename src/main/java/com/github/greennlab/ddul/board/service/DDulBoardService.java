package com.github.greennlab.ddul.board.service;

import com.github.greennlab.ddul.board.Board;
import com.github.greennlab.ddul.board.BoardComment;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DDulBoardService {

  Page<Board> getPage(Map<String, Object> params, Pageable pageable);

  Board getArticle(Long id);

  List<BoardComment> getComments(Long id);

  Board save(Board board);

  Board delete(Long id);
}
