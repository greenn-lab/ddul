package io.github.greennlab.ddul.infrastructure.article.service;

import io.github.greennlab.ddul.infrastructure.article.ArticleComment;
import io.github.greennlab.ddul.infrastructure.article.repository.DDulArticleCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service("DDulArticleCommentService")
@Transactional
@RequiredArgsConstructor
public class ArticleCommentServiceImpl implements ArticleCommentService {

  private final DDulArticleCommentRepository repository;

  private final PasswordEncoder passwordEncoder;


  @Override
  public Page<ArticleComment> paginate(Long articleId, Pageable pageable) {
    return repository.findAllByArticleId(articleId, pageable);
  }

  @Override
  public ArticleComment select(Long id) {
    return repository.findById(id).orElse(null);
  }

  @Override
  public ArticleComment insert(ArticleComment comment) {
    if (null != comment.getPassword()) {
      comment.setPassword(passwordEncoder.encode(comment.getPassword()));
    }

    if (null == comment.getPid()) {
      final ArticleComment saved = repository.save(comment);
      final Long newId = saved.getId();

      saved.setBunch(newId);

      repository.updateBunch(newId);

      return saved;
    } else {
      repository.findById(comment.getPid()).ifPresent(parent -> {
        final Long bunch = parent.getBunch();
        final int sequel = parent.getSequel() + 1;

        comment.setBunch(bunch);
        comment.setSequel(sequel);
        comment.setDepth(parent.getDepth() + 1);

        repository.shoveUpReplyOrders(bunch, sequel);
      });

      return repository.save(comment);
    }
  }

  @Override
  public ArticleComment update(ArticleComment comment) {
    if (ObjectUtils.isEmpty(comment.getPassword())) {
      repository.findById(comment.getId()).ifPresent(i -> comment.setPassword(i.getPassword()));
    } else {
      comment.setPassword(passwordEncoder.encode(comment.getPassword()));
    }

    return repository.save(comment);
  }

  @Override
  public void delete(Long id) {
    repository.findById(id).ifPresent(comment -> {
      comment.setRemoval(true);
      repository.save(comment);
    });
  }
}
