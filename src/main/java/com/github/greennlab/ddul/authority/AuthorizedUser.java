package com.github.greennlab.ddul.authority;

import com.github.greennlab.ddul.user.User;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class AuthorizedUser implements UserDetails {

  private static final long serialVersionUID = 1601156011500844797L;


  @Getter
  private final User user;

  private final Set<? extends GrantedAuthority> authorities;

  public AuthorizedUser(String username, String... authorities) {
    this.user = new User();
    this.user.setUsername(username);

    this.authorities = Arrays.stream(authorities).map(
        SimpleGrantedAuthority::new).collect(Collectors.toSet());
  }

  public static Optional<User> currently() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (null == authentication) {
      return Optional.empty();
    }

    return Optional.ofNullable(null == authentication.getPrincipal() ? null :
        ((AuthorizedUser) authentication.getPrincipal()).getUser());
  }


  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !user.isLock();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    final LocalDate expired = user.getPasswordExpired();
    return expired == null || expired.isAfter(LocalDate.now());
  }

  @Override
  public boolean isEnabled() {
    return isAccountNonExpired() && isCredentialsNonExpired();
  }

}
