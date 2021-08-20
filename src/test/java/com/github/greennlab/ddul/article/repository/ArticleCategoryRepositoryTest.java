package com.github.greennlab.ddul.article.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.greennlab.ddul.article.ArticleCategory;
import com.github.greennlab.ddul.entity.JsonMap;
import com.github.greennlab.ddul.test.DataJpaTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ArticleCategoryRepositoryTest extends DataJpaTest {

  @Autowired
  ArticleCategoryRepository repository;

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
