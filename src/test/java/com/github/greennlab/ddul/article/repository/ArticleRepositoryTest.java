package com.github.greennlab.ddul.article.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.greennlab.ddul.article.Article;
import com.github.greennlab.ddul.test.DataJpaDBTest;
import com.github.greennlab.ddul.user.User;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

class ArticleRepositoryTest extends DataJpaDBTest {

  @Autowired
  ArticleRepository repository;

  @Test
  void shouldSave() {
    final User user = new User();
    user.setId(-1L);

    final Article article = new Article();
    article.setCategory("notice");
    article.setUser(user);
    article.setAuthor("tester");
    article.setEmail("tester@test.com");
    article.setPassword("test123$");
    article.setSecret(true);
    article.setTitle("I'm into this world.");
    article.setContent("<h1>Hi there!<small>to anybody</small></h1>");

    repository.save(article);
  }

  @Test
  void shouldGetPage() {
    final Page<Article> page = repository.pageBy(null, null, PageRequest.of(0, 20));

    assertThat(page).isNotNull();
    assertThat(page.getTotalElements()).isEqualTo(1);
  }

  @Test
  void shouldGetPageIfMatched() {
    final PageRequest pagable = PageRequest.of(0, 20);

    final Page<Article> byAuthor = repository.pageBy("1", "tester", pagable);
    assertThat(byAuthor.getTotalElements()).isEqualTo(1);

    final Page<Article> byTitle = repository.pageBy("2", "world", pagable);
    assertThat(byTitle.getTotalElements()).isEqualTo(1);

    final Page<Article> byContent = repository.pageBy("3", "body", pagable);
    assertThat(byContent.getTotalElements()).isEqualTo(1);

    final Page<Article> byAll = repository.pageBy(null, "any", pagable);
    assertThat(byAll.getTotalElements()).isEqualTo(1);
  }

  @Test
  void shouldGet() {
    final Optional<Article> one = repository.findById(1L);

    final User user = one.get().getUser();
    assertThat(one).isNotNull();
  }


}
