package io.github.greennlab.ddul.infrastructure.authority.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import io.github.greennlab.ddul.infrastructure.authority.Authority;
import io.github.greennlab.ddul.infrastructure.authority.MappedMenuAuthority;
import io.github.greennlab.ddul.infrastructure.authority.repository.DDulMappedMenuAuthorityRepository;
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
  DDulMappedMenuAuthorityRepository authorityMenuRepository;

  @Test
  void shouldGetMenuRoles() {
    final Set<MappedMenuAuthority> mapped = new HashSet<>();

    final MappedMenuAuthority mam1 = new MappedMenuAuthority();
    mam1.setMenuId(-1L);
    mam1.setAuthority(new Authority("HI"));
    mapped.add(mam1);

    MappedMenuAuthority mam2 = new MappedMenuAuthority();
    mam2.setMenuId(-1L);
    mam2.setAuthority(new Authority("HELLO"));
    mapped.add(mam2);

    given(authorityMenuRepository.findAll()).willReturn(mapped);

    final Map<Long, Set<Authority>> allMenuAuthorities = service.getAllMenuAuthorities();

    assertThat(allMenuAuthorities).hasSize(1);
  }

}
