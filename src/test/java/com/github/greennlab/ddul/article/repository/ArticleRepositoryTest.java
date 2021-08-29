package com.github.greennlab.ddul.article.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.greennlab.ddul.article.Article;
import com.github.greennlab.ddul.article.dto.ArticleOutputDTO;
import com.github.greennlab.ddul.test.DataJpaTest;
import com.github.greennlab.ddul.user.User;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

class ArticleRepositoryTest extends DataJpaTest {

  @Autowired
  ArticleRepository repository;

  @BeforeEach
  void beforeAll() {
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
    final Page<ArticleOutputDTO> page = repository.findAllBy("notice", null, null,
        PageRequest.of(0, 20));

    assertThat(page).isNotNull();
    assertThat(page.getTotalElements()).isEqualTo(1);
  }

  @Test
  void shouldGetPageIfMatched() {
    final PageRequest pageable = PageRequest.of(0, 20);

    final Page<ArticleOutputDTO> byAuthor = repository.findAllBy("notice", "1", "tester",
        pageable);
    assertThat(byAuthor.getTotalElements()).isEqualTo(1);

    final Page<ArticleOutputDTO> byTitle = repository.findAllBy("notice", "2", "world",
        pageable);
    assertThat(byTitle.getTotalElements()).isEqualTo(1);

    final Page<ArticleOutputDTO> byContent = repository.findAllBy("notice", "3", "body",
        pageable);
    assertThat(byContent.getTotalElements()).isEqualTo(1);

    final Page<ArticleOutputDTO> byAll = repository.findAllBy("notice", null, "any", pageable);
    assertThat(byAll.getTotalElements()).isEqualTo(1);
  }

  @Test
  void shouldGet() {
    final Optional<Article> one = repository.findById(1L);

    final Article article = one.orElseThrow(NullPointerException::new);
    assertThat(one).isNotNull();
  }

}
