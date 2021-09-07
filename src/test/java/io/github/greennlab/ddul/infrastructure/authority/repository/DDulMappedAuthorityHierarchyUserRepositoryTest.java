package io.github.greennlab.ddul.infrastructure.authority.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.greennlab.ddul.infrastructure.authority.Authority;
import io.github.greennlab.ddul.infrastructure.authority.MappedUserAuthority;
import io.github.greennlab.ddul.test.DataJpaTest;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DDulMappedAuthorityHierarchyUserRepositoryTest extends DataJpaTest {

  @Autowired
  DDulMappedUserAuthorityRepository repository;

  @Test
  void shouldGetAuthorityByUserId() {
    final Set<MappedUserAuthority> mapped = repository.findAllByUserId(-1L);
    final Set<Authority> authorities = mapped.stream().map(MappedUserAuthority::getAuthority)
        .collect(Collectors.toSet());

    assertThat(authorities).hasSizeGreaterThanOrEqualTo(5);
  }

  @Test
  void shouldStored() {
    final MappedUserAuthority entity = new MappedUserAuthority();
    entity.setUserId(-1L);
    final Authority admin = new Authority("ADMIN");
    admin.setId(0L);

    entity.setAuthority(admin);

    final HashSet<MappedUserAuthority> entities = Sets.newHashSet(
        Collections.singletonList(entity));
    final Set<MappedUserAuthority> mappedUserAuthorities = repository.saveAll(entities);

    assertThat(mappedUserAuthorities).hasSize(1);
  }

}
