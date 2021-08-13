package com.github.greennlab.ddul.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.greennlab.ddul.authority.Authority;
import com.github.greennlab.ddul.entity.BaseEntity;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

@Entity
@Table(name = "USER")
@Getter
@Setter
public class User extends BaseEntity implements UserDetails {

  public static final String REGEXP_PASSWORD =
      "^(?=.*[~!@#$%^&*()_+`\\-=\\[\\]{};':\",./<>?])(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])\\S{6,}$";

  private static final long serialVersionUID = -7382145646927293876L;


  private String username = "{ghost}";

  @JsonIgnore
  private String password;

  @Column(name = "PASSWORD_EXP")
  private LocalDate passwordExpired;

  private String email;
  private String name;
  private boolean lock;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "USER_ID")
  @JsonIgnore
  private Set<UserAuthority> userAuthorities = new HashSet<>();


  public static Optional<User> authenticated() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return Optional.of((User) authentication.getPrincipal());
  }


  private Set<Authority> spreadAllAuthorities(Collection<Authority> authorities) {
    final Set<Authority> result = new HashSet<>();

    for (Authority authority : authorities) {
      result.add(authority);

      if (!ObjectUtils.isEmpty(authority.getChildren())) {
        result.addAll(spreadAllAuthorities(authority.getChildren()));
      }
    }

    return result;
  }

  // -------------------------------------------------------
  // Security
  // -------------------------------------------------------
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return spreadAllAuthorities(getUserAuthorities().stream()
        .map(UserAuthority::getAuthority)
        .collect(Collectors.toSet()));
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !isLock();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return getPasswordExpired() == null
        || getPasswordExpired().isAfter(LocalDate.now());
  }

  @Override
  public boolean isEnabled() {
    return isAccountNonExpired() && isCredentialsNonExpired();
  }
}
