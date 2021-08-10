package com.github.greennlab.ddul.board.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.greennlab.ddul.board.Board;
import com.github.greennlab.ddul.board.BoardContent;
import com.github.greennlab.ddul.test.DataJpaDBTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BoardRepositoryTest extends DataJpaDBTest {

  @Autowired
  BoardRepository repository;

  @Autowired
  BoardContentRepository contentRepository;


  @Test
  void save() {
    Board board = new Board();
    board.setGroup("notice");
    board.setTitle("hi");

    BoardContent content = new BoardContent();
    content.setBody("<h1>hello!<small class=\"subtitle--2\">nice to meet you~</small></h1>");
    content.setBoard(board);

    board.setContent(content);

    contentRepository.save(content);
    final Board save = repository.save(board);

    assertThat(save.getId()).isNotNull();
  }

  @Test
  void findById() {
    final Optional<Board> item = repository.findById(15L);
    assertThat(item).isNotNull();
  }


}
