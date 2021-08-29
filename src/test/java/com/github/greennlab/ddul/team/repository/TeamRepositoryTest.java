package com.github.greennlab.ddul.team.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.greennlab.ddul.team.Team;
import com.github.greennlab.ddul.test.DataJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class TeamRepositoryTest extends DataJpaTest {

  @Autowired
  TeamRepository repository;

  @Test
  void shouldGetReadChildren() {
    final Team root = repository.findById(-900L).orElseThrow(NullPointerException::new);

    assertThat(root).isNotNull();
  }
}
