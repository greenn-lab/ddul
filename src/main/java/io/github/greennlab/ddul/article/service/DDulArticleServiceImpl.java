package io.github.greennlab.ddul.article.service;

import static io.github.greennlab.ddul.article.dto.ArticleOutputDTO.mapped;

import io.github.greennlab.ddul.article.Article;
import io.github.greennlab.ddul.article.dto.ArticleOutputDTO;
import io.github.greennlab.ddul.article.repository.DDulArticleRepository;
import io.github.greennlab.ddul.authority.AuthorizedUser;
import io.github.greennlab.ddul.user.User;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class DDulArticleServiceImpl implements ArticleService {

  private final DDulArticleRepository repository;


  @Override
  public Page<ArticleOutputDTO> pageBy(String category, String searchType, String keyword,
      Pageable pageable) {

    final Page<Article> paged = repository.findAllBy(category, searchType, keyword, pageable);

    return PageableExecutionUtils.getPage(
        paged.getContent().stream().map(mapped::to).collect(Collectors.toList()),
        pageable,
        paged::getTotalElements);
  }

  @Transactional
  @Override
  public ArticleOutputDTO select(Long id) {
    final Article article = repository.findById(id).orElse(null);
    return mapped.to(article);
  }

  @Transactional
  @Override
  public ArticleOutputDTO insert(final Article article) {
    fillUserInfo(article);

    final Article saved;

    // 새로운 게시글이 입력될 때 BID (답글이면 BID 가 답글 대상 게시물에서 가져옴) 가
    // 없으면, 현재 게시물 ID 와 동일하게 입력해서 정렬 기준을 맞춰요.
    // 있다면, 답글 대상 게시물에서 가져온 depth 와 order 를 조정해요.
    if (null == article.getPid()) {
      saved = repository.save(article);

      final Long newId = saved.getId();
      saved.setBid(newId);

      repository.updateBid(newId);
    } else {
      repository.findById(article.getPid()).ifPresent(parent -> {
        final Long bid = parent.getBid();
        final int order = parent.getOrder() + 1;

        article.setBid(bid);
        article.setOrder(order);
        article.setDepth(parent.getDepth() + 1);

        repository.updateRepliesOrder(bid, order);
      });

      saved = repository.save(article);
    }

    return mapped.to(saved);
  }

  @Transactional
  @Override
  public ArticleOutputDTO update(Article article) {
    return mapped.to(repository.save(article));
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }


  private void fillUserInfo(Article article) {
    final User user = AuthorizedUser.currently().orElseThrow(NullPointerException::new);

    if (ObjectUtils.isEmpty(article.getUser())) {
      article.setUser(user);
    }

    if (ObjectUtils.isEmpty(article.getAuthor())) {
      article.setAuthor(user.getName());
    }

    if (ObjectUtils.isEmpty(article.getEmail())) {
      article.setEmail(user.getEmail());
    }
  }

}
