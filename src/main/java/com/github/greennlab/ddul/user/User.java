package com.github.greennlab.ddul.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.greennlab.ddul.authority.Authority;
import com.github.greennlab.ddul.entity.Auditor;
import com.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class User extends Auditor implements UserDetails {

  public static final UserOf mapped = Mappers.getMapper(UserOf.class);

  private static final long serialVersionUID = -7382145646927293876L;


  @Id
  @GeneratedValue
  private Long id;

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


  // -------------------------------------------------------
  // Underling
  // -------------------------------------------------------
  @Mapper
  public interface UserOf extends EntityDtoMapping<User, Dto> {

  }

  @Getter
  @Setter
  public static class Dto {

    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private LocalDate passwordExpired;

    @Email
    private String email;

    private String name;

    private boolean lock;

  }

}
