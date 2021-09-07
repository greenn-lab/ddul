package io.github.greennlab.ddul.infrastructure.article.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.greennlab.ddul.infrastructure.article.Article;
import io.github.greennlab.ddul.entity.JsonMap;
import io.github.greennlab.ddul.test.DataJpaTest;
import io.github.greennlab.ddul.infrastructure.user.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

@Rollback(false)
@Slf4j
class DDulArticleRepositoryTest extends DataJpaTest {

  @Autowired
  DDulArticleRepository repository;

  @BeforeEach
  void beforeAll() {
    final User user = new User();
    user.setId(-1L);
    user.setUsername("hello");

    final Article article = new Article();
    article.setCategory("notice");
    article.setUser(user);
//    article.setAuthor("tester");
//    article.setEmail("tester@test.com");
    article.setPassword("test123$");
    article.setSecret(true);
    article.setTitle("I'm into this world.");
    article.setContent("<h1>Hi there!<small>to anybody</small></h1>");

    JsonMap extra = new JsonMap();
    extra.put("hi", 1);
    extra.put("hello", BigDecimal.valueOf(Long.MAX_VALUE));
    extra.put("nihao", true);
    extra.put("bonjour", LocalDateTime.now());
    article.setExtra(extra);

    repository.save(article);
  }

  @Test
  void shouldGetPage() {
    final Page<Article> page = repository.findAllBy("notice", null, null,
        PageRequest.of(0, 3));

    assertThat(page).isNotNull();
    assertThat(page.getTotalElements()).isEqualTo(8);
  }

  @Test
  void shouldGetONe() {
    final Article article = repository.findById(-1L).orElseThrow(NullPointerException::new);

    assertThat(article).isNotNull();
    assertThat(article.getExtra()).containsKeys("hi", "hello", "nihao", "bonjour");

    logger.info("extra: {}", article.getExtra());
  }

  @Test
  void shouldGetPageIfMatched() {
    final PageRequest pageable = PageRequest.of(0, 20);

    final Page<Article> byAuthor = repository.findAllBy("notice", "1", "tester",
        pageable);
    assertThat(byAuthor.getTotalElements()).isEqualTo(1);

    final Page<Article> byTitle = repository.findAllBy("notice", "2", "world",
        pageable);
    assertThat(byTitle.getTotalElements()).isEqualTo(1);

    final Page<Article> byContent = repository.findAllBy("notice", "3", "body",
        pageable);
    assertThat(byContent.getTotalElements()).isEqualTo(1);

    final Page<Article> byAll = repository.findAllBy("notice", null, "any", pageable);
    assertThat(byAll.getTotalElements()).isEqualTo(1);
  }

  @Test
  void shouldGet() {
    final Optional<Article> one = repository.findById(1L);
    assertThat(one).isNotNull();
  }

}
