package io.github.greennlab.ddul.authority.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.greennlab.ddul.authority.Authority;
import io.github.greennlab.ddul.authority.MapAuthorityUser;
import io.github.greennlab.ddul.test.DataJpaTest;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DDulMapAuthorityUserRepositoryTest extends DataJpaTest {

  @Autowired
  DDulMapAuthorityUserRepository repository;

  @Test
  void shouldGetAuthorityByUserId() {
    final Set<MapAuthorityUser> mapped = repository.findAllByUserId(-1L);
    final Set<Authority> authorities = mapped.stream().map(MapAuthorityUser::getAuthority)
        .collect(Collectors.toSet());

    assertThat(Authority.spreadAll(authorities)).hasSizeGreaterThanOrEqualTo(5);
  }

  @Test
  void shouldStored() {
    final MapAuthorityUser entity = new MapAuthorityUser();
    entity.setUserId(-1L);
    final Authority admin = new Authority("ADMIN");
    admin.setId(0L);

    entity.setAuthority(admin);

    final HashSet<MapAuthorityUser> entities = Sets.newHashSet(Collections.singletonList(entity));
    final Set<MapAuthorityUser> mapAuthorityUsers = repository.saveAll(entities);

    assertThat(mapAuthorityUsers).hasSize(1);
  }

}
