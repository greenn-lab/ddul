package io.github.greennlab.ddul.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.github.greennlab.ddul.test.DataJpaTest;
import io.github.greennlab.ddul.infrastructure.user.User;
import io.github.greennlab.ddul.infrastructure.user.repository.DDulUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DDulUserRepositoryTest extends DataJpaTest {

  @Autowired
  DDulUserRepository repository;

  @Test
  void shouldGetUserWithUserAuthorities() {
    final User user = repository.findByUsername("tester");

    assertThat(user).isNotNull();
  }

  @Test
  void shouldSaveUser() {
    final User newUser = new User();
    newUser.setUsername("TESTER");
    newUser.setPassword("password");

    final User save = repository.saveAndFlush(newUser);
    assertThat(save.getId()).isNotNull();
  }

}
