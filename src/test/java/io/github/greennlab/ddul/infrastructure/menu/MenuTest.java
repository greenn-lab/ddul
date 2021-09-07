package io.github.greennlab.ddul.infrastructure.menu;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MenuTest {

  @Test
  void shouldBeEqual() {
    Menu m1 = new Menu();
    m1.setId(-1L);
    m1.setName("hi");

    Menu m2 = new Menu();
    m2.setId(-1L);
    m2.setName("hello");

    assertThat(m1).isEqualTo(m2);
  }

}
