package com.github.greennlab.ddul.user.service;

import com.github.greennlab.ddul.authority.Authority;
import com.github.greennlab.ddul.user.AuthorizedUser;
import com.github.greennlab.ddul.user.User;
import com.github.greennlab.ddul.user.UserAuthority;
import com.github.greennlab.ddul.user.repository.DDulUserAuthorityRepository;
import com.github.greennlab.ddul.user.repository.DDulUserRepository;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class DDulUserService implements UserDetailsService {

  private final DDulUserRepository repository;

  private final DDulUserAuthorityRepository authorityRepository;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final User user = repository.findByUsername(username);

    if (null == user) {
      throw new UsernameNotFoundException(username);
    }

    final Set<Authority> authorities = authorityRepository
        .findAllByUserId(user.getId())
        .stream()
        .map(UserAuthority::getAuthority)
        .collect(Collectors.toSet());

    return new AuthorizedUser(user, spreadAllAuthorities(authorities));
  }


  private Set<GrantedAuthority> spreadAllAuthorities(Collection<Authority> authorities) {
    final Set<GrantedAuthority> result = new HashSet<>();

    for (Authority authority : authorities) {
      result.add(authority);

      if (!ObjectUtils.isEmpty(authority.getChildren())) {
        result.addAll(spreadAllAuthorities(authority.getChildren()));
      }
    }

    return result;
  }
}
