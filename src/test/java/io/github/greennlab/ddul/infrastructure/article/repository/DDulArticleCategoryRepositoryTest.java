package io.github.greennlab.ddul.infrastructure.article.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.greennlab.ddul.infrastructure.article.ArticleCategory;
import io.github.greennlab.ddul.entity.JsonMap;
import io.github.greennlab.ddul.test.DataJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DDulArticleCategoryRepositoryTest extends DataJpaTest {

  @Autowired
  DDulArticleCategoryRepository repository;

  void save() {
    final ArticleCategory ac = new ArticleCategory();
    ac.setCategory("NOTICE");
    ac.setDescription("Hello");

    final JsonMap props = new JsonMap();
    props.put("hi", 123D);
    props.put("nice", "to meet you");
    ac.setProps(props);

    repository.save(ac);
  }

  @Test
  void shouldGet() {
    save();

    final ArticleCategory notice = repository.findByCategory("NOTICE");
    assertThat(notice.getProps()).hasSize(2);
  }

}
