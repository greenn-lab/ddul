package io.github.greennlab.ddul.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.greennlab.ddul.test.DataJpaDBTest;
import io.github.greennlab.ddul.user.User;
import io.github.greennlab.ddul.user.repository.DDulUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DDulUserRepositoryTest extends DataJpaDBTest {

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


  @Test
  void serialize() throws JsonProcessingException {
    System.out.println(new ObjectMapper().writeValueAsString(new User()));
  }


}
