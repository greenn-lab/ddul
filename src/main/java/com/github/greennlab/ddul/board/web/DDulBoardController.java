package com.github.greennlab.ddul.board.web;

import com.github.greennlab.ddul.board.Board;
import com.github.greennlab.ddul.board.BoardDTO;
import com.github.greennlab.ddul.board.service.DDulBoardService;
import com.github.greennlab.ddul.user.User;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/_board/{group}")
@RequiredArgsConstructor
public class DDulBoardController {

  private final DDulBoardService service;


  @GetMapping
  public Page<BoardDTO> list(
      @PathVariable String group,
      @RequestParam String searchType,
      @RequestParam String keyword,
      Pageable pageable) {
    return null;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BoardDTO save(
      @PathVariable String group,
      @Valid @RequestBody BoardDTO board,
      @AuthenticationPrincipal User user) {
    board.setGroup(group);
    return service.save(board);
  }

}
