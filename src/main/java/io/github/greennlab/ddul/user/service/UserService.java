package io.github.greennlab.ddul.user.service;

import io.github.greennlab.ddul.authority.AuthorityHierarchy;
import io.github.greennlab.ddul.authority.AuthorizedUser;
import io.github.greennlab.ddul.authority.repository.DDulMappedAuthorityUserRepository;
import io.github.greennlab.ddul.user.User;
import io.github.greennlab.ddul.user.repository.DDulUserRepository;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("DDulUserService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final DDulUserRepository repository;

  private final DDulMappedAuthorityUserRepository mappedAuthorityUserRepository;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final User user = repository.findByUsername(username);

    if (null == user) {
      throw new UsernameNotFoundException(username);
    }

    final Set<AuthorityHierarchy> authorities = new HashSet<>();

    // TODO mapping user-authority

    return new AuthorizedUser(user, AuthorityHierarchy.spreadAll(authorities));
  }

}
