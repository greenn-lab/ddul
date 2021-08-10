package com.github.greennlab.ddul.board.service;

import com.github.greennlab.ddul.board.Board;
import com.github.greennlab.ddul.board.BoardContent;
import com.github.greennlab.ddul.board.BoardDTO;
import com.github.greennlab.ddul.board.BoardReply;
import com.github.greennlab.ddul.board.repository.BoardContentRepository;
import com.github.greennlab.ddul.board.repository.BoardRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class DDulBoardServiceImpl implements DDulBoardService {

  private final BoardRepository repository;

  private final BoardContentRepository contentRepository;


  @Override
  public Page<BoardDTO> getList(String searchType, String keyword, Pageable pageable) {
    return null;
  }

  @Override
  public BoardDTO getArticle(Long id) {
    return null;
  }

  @Override
  public List<BoardReply> getReplies(Long id) {
    return null;
  }

  @Override
  public BoardDTO save(BoardDTO dto) {
    if (!ObjectUtils.isEmpty(dto.getRemoveFileIds())) {
      removeFileById(dto.getRemoveFileIds().toArray(new String[0]));
    }

    Board board = BoardDTO.mapped.by(dto);
    BoardContent content = board.getContent();

    content.setBoard(board);
    board.setContent(content);

    contentRepository.save(content);
    board = repository.save(board);

    return BoardDTO.mapped.to(board);
  }

  @Override
  public void removeFileById(String... fileId) {

  }
}
