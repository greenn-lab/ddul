package io.github.greennlab.ddul.infrastructure.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.greennlab.ddul.test.DataJpaTest;
import io.github.greennlab.ddul.infrastructure.user.MappedUserTeam;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DDulMappedUserTeamRepositoryTest extends DataJpaTest {

  @Autowired
  DDulMappedUserTeamRepository repository;

  @Test
  void shouldGetAllByUserId() {
    final Set<MappedUserTeam> all = repository.findAllByUserId(-1L);

    assertThat(all).hasSizeGreaterThanOrEqualTo(3);
  }

}
