package io.github.greennlab.ddul.authority;

import com.fasterxml.jackson.annotation.JsonValue;
import io.github.greennlab.ddul.entity.BaseEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.ObjectUtils;

@Entity
@Table(name = "AUTHORITY")
@Getter
@Setter
@NoArgsConstructor
public class Authority extends BaseEntity implements GrantedAuthority {

  private static final long serialVersionUID = 6109880835618935721L;


  private String role;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "PID", insertable = false, updatable = false)
  private List<Authority> children = new ArrayList<>();


  public Authority(String role) {
    this.role = role;
  }


  public static Set<Authority> spreadAll(Collection<Authority> authorities) {
    final Set<Authority> result = new HashSet<>();

    for (Authority authority : authorities) {
      result.add(authority);

      if (!ObjectUtils.isEmpty(authority.getChildren())) {
        result.addAll(spreadAll(authority.getChildren()));
      }
    }

    return result;
  }


  //--------------------------------------------------
  // Role
  //--------------------------------------------------
  @Override
  public String getAuthority() {
    return role;
  }


  @JsonValue
  @Override
  public String toString() {
    return role;
  }
}
