package com.github.greennlab.ddul.team.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.github.greennlab.ddul.team.TeamHierarchy;
import com.github.greennlab.ddul.test.DataJpaTest;
import org.assertj.core.api.Assertions;
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
