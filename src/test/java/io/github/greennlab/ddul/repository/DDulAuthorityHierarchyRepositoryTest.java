package io.github.greennlab.ddul.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.greennlab.ddul.infrastructure.authority.AuthorityHierarchy;
import io.github.greennlab.ddul.infrastructure.authority.repository.DDulAuthorityHierarchyRepository;
import io.github.greennlab.ddul.test.DataJpaTest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DDulAuthorityHierarchyRepositoryTest extends DataJpaTest {

  @Autowired
  private DDulAuthorityHierarchyRepository repository;

  @Test
  void shouldGetHierarchyAuthorities() {
    final AuthorityHierarchy authority = repository.findById(-2L);

    assertThat(authority).isNotNull();

    final List<AuthorityHierarchy> authorities = getAllAsFlat(authority).collect(
        Collectors.toList());
    assertThat(authorities.size()).isGreaterThanOrEqualTo(4);
  }

  private Stream<AuthorityHierarchy> getAllAsFlat(AuthorityHierarchy authorityHierarchy) {
    return Stream.concat(Stream.of(authorityHierarchy),
        authorityHierarchy.getChildren().stream().flatMap(this::getAllAsFlat));
  }


}
