package io.github.greennlab.ddul.user.service;

import io.github.greennlab.ddul.authority.Authority;
import io.github.greennlab.ddul.authority.AuthorizedUser;
import io.github.greennlab.ddul.authority.MapAuthorityUser;
import io.github.greennlab.ddul.authority.repository.DDulMapAuthorityUserRepository;
import io.github.greennlab.ddul.user.User;
import io.github.greennlab.ddul.user.repository.DDulUserRepository;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("DDulUserService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final DDulUserRepository repository;

  private final DDulMapAuthorityUserRepository authorityRepository;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final User user = repository.findByUsername(username);

    if (null == user) {
      throw new UsernameNotFoundException(username);
    }

    final Set<Authority> authorities = authorityRepository
        .findAllByUserId(user.getId())
        .stream()
        .map(MapAuthorityUser::getAuthority)
        .collect(Collectors.toSet());

    return new AuthorizedUser(user, Authority.spreadAll(authorities));
  }

}
