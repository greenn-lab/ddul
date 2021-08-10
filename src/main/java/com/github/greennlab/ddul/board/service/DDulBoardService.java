package com.github.greennlab.ddul.board.service;

import com.github.greennlab.ddul.board.BoardDTO;
import com.github.greennlab.ddul.board.BoardComment;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DDulBoardService {

  Page<BoardDTO> getList(String searchType, String keyword, Pageable pageable);

  BoardDTO getArticle(Long id);

  List<BoardComment> getReplies(Long id);

  BoardDTO save(BoardDTO board);

  void removeFileById(String... fileId);

}
