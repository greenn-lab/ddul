package com.github.greennlab.ddul.board.repository;

import com.github.greennlab.ddul.SqlLoggingConfiguration;
import com.github.greennlab.ddul.board.Board;
import com.github.greennlab.ddul.board.BoardContent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(SqlLoggingConfiguration.class)
@Rollback(false)
class BoardRepositoryTest {

  @Autowired
  BoardRepository repository;

  @Test
  void shouldConnect() {
    Board board = new Board();
    board.setGroup("notice");
    board.setTitle("hi");

    BoardContent content = new BoardContent();
    content.setBody("hello!");

//    board.setContent(content);

    repository.save(board);
  }

}
