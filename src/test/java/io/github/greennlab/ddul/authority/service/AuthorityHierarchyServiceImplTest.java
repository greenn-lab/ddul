package io.github.greennlab.ddul.authority.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import io.github.greennlab.ddul.authority.Authority;
import io.github.greennlab.ddul.authority.MappedMenuAuthority;
import io.github.greennlab.ddul.authority.dto.AuthorityOutputDTO;
import io.github.greennlab.ddul.authority.repository.DDulMappedMenuRepositoryAuthority;
import io.github.greennlab.ddul.menu.Menu;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest()
class AuthorityHierarchyServiceImplTest {

  @Autowired
  AuthorityService service;

  @MockBean
  DDulMappedMenuRepositoryAuthority authorityMenuRepository;

  @Test
  void shouldGetMenuRoles() {
    final Set<MappedMenuAuthority> mapped = new HashSet<>();

    final Menu menu1 = new Menu();
    menu1.setId(-1L);
    final MappedMenuAuthority mam1 = new MappedMenuAuthority();
    mam1.setMenu(menu1);
    mam1.setAuthority(new Authority("HI"));
    mapped.add(mam1);

    final Menu menu2 = new Menu();
    menu2.setId(-1L);
    MappedMenuAuthority mam2 = new MappedMenuAuthority();
    mam2.setMenu(menu2);
    mam2.setAuthority(new Authority("HELLO"));
    mapped.add(mam2);

    given(authorityMenuRepository.findAll()).willReturn(mapped);

    final Map<Long, Set<AuthorityOutputDTO>> menuRoles = service.getMenuAuthorities();

    assertThat(menuRoles).hasSize(1);
//    assertThat(menuRoles).hasEntrySatisfying(-1L, i -> {
//      i.contains("Hi");
//    });
//    assertThat(menuRoles).hasEntrySatisfying(-1L, i -> i.contains("HELLO"));
  }

}
