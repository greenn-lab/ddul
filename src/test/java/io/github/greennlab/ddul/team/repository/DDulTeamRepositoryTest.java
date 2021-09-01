package io.github.greennlab.ddul.team.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.greennlab.ddul.team.Team;
import io.github.greennlab.ddul.test.DataJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class DDulTeamRepositoryTest extends DataJpaTest {

  @Autowired
  DDulTeamRepository repository;

  @Test
  void shouldGetReadChildren() {
    final Team root = repository.findById(-900L).orElseThrow(NullPointerException::new);

    assertThat(root).isNotNull();
  }
}
