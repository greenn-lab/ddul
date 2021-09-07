package io.github.greennlab.ddul.infrastructure.article.service;

import graphql.kickstart.tools.GraphQLQueryResolver;
import io.github.greennlab.ddul.infrastructure.article.Article;
import io.github.greennlab.ddul.infrastructure.article.repository.DDulArticleRepository;
import io.github.greennlab.ddul.infrastructure.authority.AuthorizedUser;
import io.github.greennlab.ddul.infrastructure.user.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service("DDulArticleService")
@Transactional
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService, GraphQLQueryResolver {

  private final DDulArticleRepository repository;


  @Override
  public Page<Article> paginate(@NonNull Pageable pageable, @NonNull String category,
      String searchType, String keyword) {
    return repository.findAllBy(category, searchType, keyword, pageable);
  }

  @Override
  public Article select(Long id) {
    return repository.findById(id).orElse(null);
  }

  @Override
  public Article insert(@NonNull final Article article) {
    fillUserInfo(article);

    final Article saved;

    // 새로운 게시글이 입력될 때 부모 ID(PID) 가
    // 없으면, 현재 게시글 ID 와 동일하게 묶음 ID(BID)를 입력해서 정렬 기준을 맞춰요.
    // 있다면, 부모 게시글에서 깊이(depth)를 +1 해주고 이후 게시글들의 정렬순서(order)를 밀어내요.
    if (null == article.getPid()) {
      saved = repository.save(article);

      final Long newId = saved.getId();
      saved.setBunch(newId);

      repository.updateBid(newId);
    } else {
      repository.findById(article.getPid()).ifPresent(parent -> {
        final Long bunch = parent.getBunch();
        final int sequel = parent.getSequel() + 1;

        article.setBunch(bunch);
        article.setSequel(sequel);
        article.setDepth(parent.getDepth() + 1);

        repository.shovedReplyOrders(bunch, sequel);
      });

      saved = repository.save(article);
    }

    return saved;
  }

  @Override
  public Article update(@NonNull Article article) {
    if (ObjectUtils.isEmpty(article.getPassword())) {
      repository.findById(article.getId()).ifPresent(i -> article.setPassword(i.getPassword()));
    }

    return repository.save(article);
  }

  @Override
  public void delete(Long id) {
    repository.findById(id).ifPresent(i -> {
      i.setRemoval(true);
      repository.save(i);
    });
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
