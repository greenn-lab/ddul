package com.github.greennlab.ddul.user;

import com.github.greennlab.ddul.authority.Authority;
import com.github.greennlab.ddul.entity.Auditor;
import com.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Where(clause = "DELETED = 'N'")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class User extends Auditor implements UserDetails {

  private static final long serialVersionUID = -7382145646927293876L;


  @Id
  @GeneratedValue
  private Long id;

  private String email;
  private String username;
  private String password;
  private LocalDate passwordExpired;
  private boolean locked;

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
    return !isLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return getPasswordExpired().isAfter(LocalDate.now());
  }

  @Override
  public boolean isEnabled() {
    return isAccountNonExpired() && isCredentialsNonExpired();
  }


  // -------------------------------------------------------
  // DTO
  // -------------------------------------------------------
  @Getter
  @Setter
  public static class Dto {

    private Long id;

    @Email
    private String email;

    private String username;

    private String password;

    private LocalDate passwordExpired;

    private boolean locked;

  }

  @Mapper
  public interface UserOf extends EntityDtoMapping<User, Dto> {
    UserOf mapped = Mappers.getMapper(UserOf.class);
  }

}
