package io.github.greennlab.ddul.authority.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.greennlab.ddul.authority.Authority;
import io.github.greennlab.ddul.authority.MappedAuthorityUser;
import io.github.greennlab.ddul.test.DataJpaTest;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DDulMappedAuthorityUserRepositoryTest extends DataJpaTest {

  @Autowired
  DDulMappedAuthorityUserRepository repository;

  @Test
  void shouldGetAuthorityByUserId() {
    final Set<MappedAuthorityUser> mapped = repository.findAllByUserId(-1L);
    final Set<Authority> authorities = mapped.stream().map(MappedAuthorityUser::getAuthority)
        .collect(Collectors.toSet());

    assertThat(Authority.spreadAll(authorities)).hasSizeGreaterThanOrEqualTo(5);
  }

  @Test
  void shouldStored() {
    final MappedAuthorityUser entity = new MappedAuthorityUser();
    entity.setUserId(-1L);
    final Authority admin = new Authority("ADMIN");
    admin.setId(0L);

    entity.setAuthority(admin);

    final HashSet<MappedAuthorityUser> entities = Sets.newHashSet(Collections.singletonList(entity));
    final Set<MappedAuthorityUser> mappedAuthorityUsers = repository.saveAll(entities);

    assertThat(mappedAuthorityUsers).hasSize(1);
  }

}
