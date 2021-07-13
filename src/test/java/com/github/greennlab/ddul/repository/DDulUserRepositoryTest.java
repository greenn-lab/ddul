package com.github.greennlab.ddul.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.greennlab.ddul.user.User;
import com.github.greennlab.ddul.user.repository.DDulUserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DDulUserRepositoryTest {

  @Autowired
  DDulUserRepository repository;

  @Test
  void shouldGetUserWithUserAuthorities() {
    final Optional<User> user = repository.findById(-1L);

    assertThat(user.orElseThrow(RuntimeException::new)).isNotNull();
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
