package io.github.greennlab.ddul.authority.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.greennlab.ddul.authority.MapAuthorityMenu;
import io.github.greennlab.ddul.test.DataJpaTest;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DDulMapAuthorityMenuRepositoryTest extends DataJpaTest {

  @Autowired
  DDulMapAuthorityMenuRepository repository;

  @Test
  void shouldFindAll() {
    final Set<MapAuthorityMenu> all = repository.findAll();

    assertThat(all).hasSizeGreaterThanOrEqualTo(3);
  }

}
