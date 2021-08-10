package com.github.greennlab.ddul.authority;

import com.github.greennlab.ddul.entity.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(
    name = "AUTHORITY",
    uniqueConstraints = @UniqueConstraint(name = "AUTHORITY_UQ1", columnNames = "ROLE")
)
@Getter
@Setter
public class Authority extends BaseEntity implements GrantedAuthority {

  private static final long serialVersionUID = 6109880835618935721L;


  private String role;

  @OneToMany
  @JoinColumn(name = "UPPER_ID")
  private List<Authority> children = new ArrayList<>();


  //--------------------------------------------------
  // Role
  //--------------------------------------------------
  @Override
  public String getAuthority() {
    return role;
  }

  public Stream<Authority> getAllAsFlat() {
    return Stream.concat(Stream.of(this), children.stream().flatMap(Authority::getAllAsFlat));
  }

}
