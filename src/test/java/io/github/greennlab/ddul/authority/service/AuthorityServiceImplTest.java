package io.github.greennlab.ddul.authority.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import io.github.greennlab.ddul.authority.Authority;
import io.github.greennlab.ddul.authority.MappedAuthorityMenu;
import io.github.greennlab.ddul.authority.repository.DDulAuthorityRepository;
import io.github.greennlab.ddul.authority.repository.DDulMappedAuthorityMenuRepository;
import io.github.greennlab.ddul.menu.Menu;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest()
class AuthorityServiceImplTest {

  @Autowired
  AuthorityService service;

  @MockBean
  DDulMappedAuthorityMenuRepository authorityMenuRepository;

  @Test
  void shouldGetMenuRoles() {
    final Set<MappedAuthorityMenu> mapped = new HashSet<>();

    final Menu menu1 = new Menu();
    menu1.setId(-1L);
    final MappedAuthorityMenu mam1 = new MappedAuthorityMenu();
    mam1.setMenu(menu1);
    mam1.setAuthority(new Authority("HI"));
    mapped.add(mam1);

    final Menu menu2 = new Menu();
    menu2.setId(-1L);
    MappedAuthorityMenu mam2 = new MappedAuthorityMenu();
    mam2.setMenu(menu2);
    mam2.setAuthority(new Authority("HELLO"));
    mapped.add(mam2);

    given(authorityMenuRepository.findAll()).willReturn(mapped);

    final Map<Long, Set<String>> menuRoles = service.getMenuRoles();

    assertThat(menuRoles).hasSize(1);
//    assertThat(menuRoles).hasEntrySatisfying(-1L, i -> {
//      i.contains("Hi");
//    });
//    assertThat(menuRoles).hasEntrySatisfying(-1L, i -> i.contains("HELLO"));
  }

}
