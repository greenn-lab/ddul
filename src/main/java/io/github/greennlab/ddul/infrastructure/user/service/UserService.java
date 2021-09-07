package io.github.greennlab.ddul.infrastructure.user.service;

import io.github.greennlab.ddul.infrastructure.authority.AuthorizedUser;
import io.github.greennlab.ddul.infrastructure.authority.service.AuthorityService;
import io.github.greennlab.ddul.infrastructure.user.User;
import io.github.greennlab.ddul.infrastructure.user.repository.DDulUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("DDulUserService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  public static final String REGEXP_PASSWORD =
      "^(?=.*[~!@#$%^&*()_+`\\-=\\[\\]{};':\",./<>?])(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])\\S{6,}$";


  private final DDulUserRepository repository;

  private final AuthorityService authorityService;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final User user = repository.findByUsername(username);

    if (null == user) {
      throw new UsernameNotFoundException(username);
    }

    return new AuthorizedUser(user, authorityService.getAllAuthorized(user));
  }

}
