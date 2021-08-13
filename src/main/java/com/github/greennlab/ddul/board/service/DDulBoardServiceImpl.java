package com.github.greennlab.ddul.board.service;

import com.github.greennlab.ddul.board.Board;
import com.github.greennlab.ddul.board.BoardAuthor;
import com.github.greennlab.ddul.board.BoardComment;
import com.github.greennlab.ddul.board.repository.BoardDao;
import com.github.greennlab.ddul.file.service.FileService;
import com.github.greennlab.ddul.user.User;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class DDulBoardServiceImpl implements DDulBoardService {

  private final BoardDao dao;

  private final FileService fileService;


  @Override
  public Page<Board> getPage(Map<String, Object> params, Pageable pageable) {
    return dao.getPage(params, pageable);
  }

  @Override
  public Board getArticle(Long id) {
    final Board board = dao.findById(id);

    board.setContent(dao.findContentById(id));
    board.setExtras(dao.findExtrasById(id));
    board.setAttachFiles(fileService.getFilesByGroup(id));

    return board;
  }

  @Override
  public List<BoardComment> getComments(Long id) {
    return dao.findCommentsById(id);
  }

  @Transactional
  @Override
  public Board save(Board board) {
    Long id = board.getId();

    fillAuthorFromLoggedUser(board);

    // 새로운 게시글이 입력될 때
    // 묶음 ID (답글이면 묶음 ID 가 답글 대상 게시물에서 가져옴) 가
    // 없으면, 현재 게시물 ID 와 동일하게 입력해서 정렬 기준을 맞춰요.
    // 있다면, 답글 대상 게시물에서 가져온 depth 와 order 를 조정해요.
    if (ObjectUtils.isEmpty(id)) {
      if (!ObjectUtils.isEmpty(board.getPid())) {
        final Board parent = dao.findById(board.getPid());
        board.setBid(parent.getBid());
        board.setOrder(parent.getOrder() + 1);
        board.setDepth(parent.getDepth() + 1);
        dao.updateReplyOrders(board);
      }

      dao.insert(board);
      dao.insertContent(board);

      id = board.getId();
    } else {
      dao.update(board);
      dao.updateContent(board);
    }

    Optional.ofNullable(board.getExtras())
        .ifPresent(i -> i.forEach(extra -> {
          extra.setBoardId(board.getId());
          dao.updateExtra(extra);
        }));

    if (!ObjectUtils.isEmpty(board.getAddFileIds())) {
      fileService.updateGroupById(id, board.getAddFileIds());
    }

    if (!ObjectUtils.isEmpty(board.getRemoveFileIds())) {
      Arrays.asList(board.getRemoveFileIds())
          .forEach(fileService::delete);
    }

    board.setAttachFiles(fileService.getFilesByGroup(id));

    return board;
  }

  private void fillAuthorFromLoggedUser(@NonNull Board board) {
    final Optional<User> authenticated = User.authenticated();
    if (!authenticated.isPresent()) {
      return;
    }

    final BoardAuthor author = board.getAuthor();
    final User user = authenticated.get();

    if (null == author.getUser()) {
      author.setUser(user);
    }

    if (ObjectUtils.isEmpty(author.getName())) {
      author.setName(user.getName());
    }
    if (ObjectUtils.isEmpty(author.getEmail())) {
      author.setEmail(user.getEmail());
    }
  }

  @Override
  public Board delete(Long id) {
    final Board board = dao.findById(id);

    if (null != board) {
      board.setRemoval(true);
      dao.delete(board);
    }

    return board;
  }

}
