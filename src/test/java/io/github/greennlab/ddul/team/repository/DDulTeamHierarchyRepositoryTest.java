package io.github.greennlab.ddul.team.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.greennlab.ddul.team.TeamHierarchy;
import io.github.greennlab.ddul.test.DataJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DDulTeamHierarchyRepositoryTest extends DataJpaTest {

  @Autowired
  DDulTeamHierarchyRepository repository;

  @Test
  void shouldGetHierarchyTeams() {
    final TeamHierarchy root = repository.findById(-900L);

    assertThat(root).isNotNull();
  }


}
