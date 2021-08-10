package com.github.greennlab.ddul.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.greennlab.ddul.authority.Authority;
import com.github.greennlab.ddul.entity.BaseEntity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "USER")
@Getter
@Setter
public class User extends BaseEntity implements UserDetails {

  public static final String REGEXP_PASSWORD =
      "^(?=.*[~!@#$%^&*()_+`\\-=\\[\\]{};':\",./<>?])(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])\\S{6,}$";

  private static final long serialVersionUID = -7382145646927293876L;


  private String username;

  @JsonIgnore
  private String password;

  @Column(name = "PASSWORD_EXP")
  private LocalDate passwordExpired;

  private String email;
  private String name;
  private boolean lock;

  @OneToMany(mappedBy = "user")
  private List<UserAuthority> userAuthorities = new ArrayList<>();

  public Stream<Authority> getFlatAuthorities() {
    return getUserAuthorities().stream()
        .map(UserAuthority::getAuthority)
        .flatMap(Authority::getAllAsFlat);
  }


  // -------------------------------------------------------
  // Security
  // -------------------------------------------------------
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return getFlatAuthorities().collect(Collectors.toList());
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
