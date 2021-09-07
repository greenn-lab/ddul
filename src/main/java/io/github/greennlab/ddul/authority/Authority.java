package io.github.greennlab.ddul.authority;

import static io.github.greennlab.ddul.entity.BaseEntity.NOT_REMOVAL;

import io.github.greennlab.ddul.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "AUTHORITY")
@Where(clause = NOT_REMOVAL)
@Getter
@Setter
@NoArgsConstructor
public class Authority extends BaseEntity implements GrantedAuthority {

  private static final long serialVersionUID = 6109880835618935721L;

  private Long pid;
  private String role;

  @Column(name = "DSC")
  private String description;


  public Authority(String role) {
    this.role = role;
  }

  @Override
  public String getAuthority() {
    return role;
  }

}
