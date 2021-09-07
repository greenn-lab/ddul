package io.github.greennlab.ddul.infrastructure.authority.service;

import io.github.greennlab.ddul.infrastructure.authority.Authority;
import io.github.greennlab.ddul.infrastructure.authority.AuthorityHierarchy;
import io.github.greennlab.ddul.infrastructure.user.User;
import java.util.Map;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;

public interface AuthorityService {

  AuthorityHierarchy getAuthorityHierarchy(Long id);

  Set<GrantedAuthority> getAllAuthorized(User user);

  Set<Authority> getAuthoritiesByUserId(Long userId);

  Map<Long, Set<Authority>> getAuthoritiesByTeams();

  Map<Long, Set<Authority>> getAllMenuAuthorities();

  Authority save(Authority authority);

  Set<Authority> saveAllMappedUserAuthorities(Long userId, Set<Authority> authorities);

  Set<Authority> saveAllMappedMenuAuthorities(Long menuId, Set<Authority> authorities);

  Set<Authority> saveAllMappedTeamAuthorities(Long teamId, Set<Authority> authorities);

}
