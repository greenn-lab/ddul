package com.github.greennlab.ddul.article.repository;

import com.github.greennlab.ddul.article.ArticleCategory;
import com.github.greennlab.ddul.test.DataJpaTest;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ArticleCategoryRepositoryTest extends DataJpaTest {

  @Autowired
  ArticleCategoryRepository repository;

  @Test
  void shouldSave() {
    final ArticleCategory ac = new ArticleCategory();
    ac.setCategory("NOTICE");
    ac.setDescription("Hello");

    final Properties props = new Properties();
    props.setProperty("hi", "hello");
    props.setProperty("nice", "to meet you");
    ac.setProps(props);

    repository.save(ac);
  }

}
