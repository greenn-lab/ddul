package io.github.greennlab.ddul.infrastructure.authority.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.greennlab.ddul.infrastructure.authority.MappedMenuAuthority;
import io.github.greennlab.ddul.test.DataJpaTest;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DDulMappedAuthorityHierarchyMenuRepositoryTest extends DataJpaTest {

  @Autowired
  DDulMappedMenuAuthorityRepository repository;

  @Test
  void shouldFindAll() {
    final Set<MappedMenuAuthority> all = repository.findAll();

    assertThat(all).hasSizeGreaterThanOrEqualTo(3);
  }

}
