package io.github.greennlab.ddul.infrastructure.authority;

import static io.github.greennlab.ddul.entity.BaseEntity.NOT_REMOVAL;

import com.fasterxml.jackson.annotation.JsonValue;
import io.github.greennlab.ddul.entity.BaseEntity;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "_AUTHORITY")
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

  @JsonValue
  @Override
  public String toString() {
    return getAuthority();
  }

  @Override
   public boolean equals(Object o) {
     if (this == o) {
       return true;
     }
     if (!(o instanceof GrantedAuthority)) {
       return false;
     }
     GrantedAuthority that = (GrantedAuthority) o;
     return role.equals(that.getAuthority());
   }

   @Override
   public int hashCode() {
     return Objects.hash(role);
   }
}
