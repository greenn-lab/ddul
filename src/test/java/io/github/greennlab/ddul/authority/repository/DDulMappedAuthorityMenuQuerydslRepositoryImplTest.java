package io.github.greennlab.ddul.authority.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.greennlab.ddul.authority.MappedAuthorityMenu;
import io.github.greennlab.ddul.test.DataJpaTest;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DDulMappedAuthorityMenuQuerydslRepositoryImplTest
    extends DataJpaTest {

  @Autowired
  DDulMappedAuthorityMenuRepository repository;

  @Test
  void shouldGet() {
    final Set<MappedAuthorityMenu> all = repository.findAll();

    assertThat(all).isNotEmpty();
  }

}
