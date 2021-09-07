package io.github.greennlab.ddul.infrastructure.authority.repository;

import io.github.greennlab.ddul.infrastructure.authority.MappedMenuAuthority;
import io.github.greennlab.ddul.test.DataJpaTest;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DDulMappedMenuAuthorityQuerydslRepositoryImplTest
    extends DataJpaTest {

  @Autowired
  DDulMappedMenuAuthorityRepository repository;

  @Test
  void shouldGetQuery() {
    final Set<MappedMenuAuthority> all = repository.findAll();

    Assertions.assertThat(all).isNotEmpty();

  }

}
