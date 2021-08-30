package com.github.greennlab.ddul.team.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.greennlab.ddul.team.TeamHierarchy;
import com.github.greennlab.ddul.test.DataJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TeamHierarchyRepositoryTest extends DataJpaTest {

  @Autowired
  TeamHierarchyRepository repository;

  @Test
  void shouldGetHierarchyTeams() {
    final TeamHierarchy root = repository.findById(-900L);

    assertThat(root).isNotNull();
  }


}
