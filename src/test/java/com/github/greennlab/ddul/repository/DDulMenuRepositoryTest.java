package com.github.greennlab.ddul.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.greennlab.ddul.menu.Menu;
import com.github.greennlab.ddul.menu.repository.DDulMenuRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class DDulMenuRepositoryTest {

  @Autowired
  DDulMenuRepository repository;

  @Test
  void shouldGetMenuAllBranches() {
    final Optional<Menu> adminMenus = repository.findById(0L);

    assertThat(adminMenus).isPresent();
  }

}
