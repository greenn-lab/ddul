package com.github.greennlab.ddul.board.service;

import com.github.greennlab.ddul.board.BoardDTO;
import com.github.greennlab.ddul.board.BoardReply;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DDulBoardService {

  Page<BoardDTO> getList(String searchType, String keyword, Pageable pageable);

  BoardDTO getArticle(Long id);

  List<BoardReply> getReplies(Long id);

  BoardDTO save(BoardDTO board);

  void removeFileById(String... fileId);

}
