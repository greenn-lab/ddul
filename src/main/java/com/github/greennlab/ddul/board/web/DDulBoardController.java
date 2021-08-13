package com.github.greennlab.ddul.board.web;

import com.github.greennlab.ddul.board.Board;
import com.github.greennlab.ddul.board.BoardComment;
import com.github.greennlab.ddul.board.service.DDulBoardService;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("_board")
@RequiredArgsConstructor
public class DDulBoardController {

  private final DDulBoardService service;


  @GetMapping("{group}")
  public Page<Board> page(
      @PathVariable String group,
      @RequestParam Map<String, Object> params,
      Pageable pageable) {

    params.put("group", group);

    return service.getPage(params, pageable);
  }

  @GetMapping("{id}")
  public Board article(@PathVariable Long id) {
    return service.getArticle(id);
  }

  @GetMapping("{id}/comments")
  public List<BoardComment> comments(@PathVariable Long id) {
    return service.getComments(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Board save(@Valid @RequestBody Board board) {
    return service.save(board);
  }
  
  @DeleteMapping("{id}")
  public Board delete(@PathVariable Long id) {
    return service.delete(id);
  }

}
