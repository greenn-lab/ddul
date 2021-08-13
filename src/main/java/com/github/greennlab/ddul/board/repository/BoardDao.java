package com.github.greennlab.ddul.board.repository;

import com.github.greennlab.ddul.board.Board;
import com.github.greennlab.ddul.board.BoardComment;
import com.github.greennlab.ddul.board.BoardContent;
import com.github.greennlab.ddul.board.BoardExtra;
import com.github.greennlab.ddul.mybatis.CrudDao;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardDao extends CrudDao<Board> {

  BoardContent findContentById(Long id);

  void updateReplyOrders(Board board);

  void insertContent(Board board);

  void updateContent(Board board);

  List<BoardExtra> findExtrasById(Long id);

  void updateExtra(BoardExtra i);

  List<BoardComment> findCommentsById(Long id);
}
