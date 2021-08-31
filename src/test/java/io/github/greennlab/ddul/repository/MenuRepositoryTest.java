package io.github.greennlab.ddul.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.greennlab.ddul.menu.Menu;
import io.github.greennlab.ddul.menu.repository.MenuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class MenuRepositoryTest {

  @Autowired
  MenuRepository repository;

  @Test
  void shouldGetMenuAllBranches() {
    final Menu adminMenus = repository.findById(0L);

    assertThat(adminMenus).isNotNull();
  }

}
