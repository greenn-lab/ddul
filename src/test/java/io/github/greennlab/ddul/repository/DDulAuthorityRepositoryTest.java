package io.github.greennlab.ddul.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.greennlab.ddul.authority.Authority;
import io.github.greennlab.ddul.authority.repository.DDulAuthorityRepository;
import io.github.greennlab.ddul.test.DataJpaTest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DDulAuthorityRepositoryTest extends DataJpaTest {

  @Autowired
  private DDulAuthorityRepository repository;

  @Test
  void shouldGetHierarchyAuthorities() {
    final Optional<Authority> authority = repository.findById(-2L);

    assertThat(authority)
        .isNotNull()
        .isPresent();

    final List<Authority> authorities = getAllAsFlat(authority.get()).collect(Collectors.toList());
    assertThat(authorities.size()).isGreaterThanOrEqualTo(4);
  }

  private Stream<Authority> getAllAsFlat(Authority authority) {
    return Stream.concat(Stream.of(authority),
        authority.getChildren().stream().flatMap(this::getAllAsFlat));
  }


}
