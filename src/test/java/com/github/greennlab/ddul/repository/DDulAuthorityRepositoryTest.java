package com.github.greennlab.ddul.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.greennlab.ddul.authority.Authority;
import com.github.greennlab.ddul.authority.repository.DDulAuthorityRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DDulAuthorityRepositoryTest {

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
    return Stream.concat(Stream.of(authority), authority.getChildren().stream().flatMap(this::getAllAsFlat));
  }



}
