package io.github.greennlab.ddul.infrastructure.authority;

import static io.github.greennlab.ddul.entity.BaseEntity.NOT_REMOVAL;

import io.github.greennlab.ddul.entity.BaseEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.ObjectUtils;

@Entity
@Table(name = "_AUTHORITY")
@Where(clause = NOT_REMOVAL)
@NoArgsConstructor
@Getter
@Setter
public class AuthorityHierarchy extends BaseEntity implements GrantedAuthority {

  private static final long serialVersionUID = 6109880835618935721L;


  private Long pid;
  private String role;

  @Column(name = "DSC")
  private String description;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "PID", insertable = false, updatable = false)
  @OrderBy("id asc")
  private List<AuthorityHierarchy> children = new ArrayList<>();


  public static Set<AuthorityHierarchy> spreadAll(Collection<AuthorityHierarchy> authorities) {
    final Set<AuthorityHierarchy> result = new HashSet<>();

    for (AuthorityHierarchy authorityHierarchy : authorities) {
      result.add(authorityHierarchy);

      if (!ObjectUtils.isEmpty(authorityHierarchy.getChildren())) {
        result.addAll(spreadAll(authorityHierarchy.getChildren()));
      }
    }

    return result;
  }

  @Override
  public String getAuthority() {
    return role;
  }

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
