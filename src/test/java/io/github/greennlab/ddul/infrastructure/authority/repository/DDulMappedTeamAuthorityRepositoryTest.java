package io.github.greennlab.ddul.infrastructure.authority.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.greennlab.ddul.infrastructure.authority.MappedTeamAuthority;
import io.github.greennlab.ddul.test.DataJpaTest;
import java.util.Arrays;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DDulMappedTeamAuthorityRepositoryTest extends DataJpaTest {

  @Autowired
  DDulMappedTeamAuthorityRepository repository;

  @Test
  void shouldGetJpaParameterINAssignableVarargs() {
    final Set<MappedTeamAuthority> all = repository.findAllByTeamIdIn(Arrays.asList(-911L, -912L));
    assertThat(all).hasSizeGreaterThanOrEqualTo(4);
  }

}
