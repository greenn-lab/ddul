package io.github.greennlab.ddul.authority;

import static io.github.greennlab.ddul.entity.BaseEntity.NOT_REMOVAL;

import com.fasterxml.jackson.annotation.JsonValue;
import io.github.greennlab.ddul.entity.BaseEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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
@Table(name = "AUTHORITY")
@Where(clause = NOT_REMOVAL)
@Getter
@Setter
@NoArgsConstructor
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


  public AuthorityHierarchy(String role) {
    this.role = role;
  }

  public AuthorityHierarchy(Authority authority) {
    setId(authority.getId());
    setPid(authority.getPid());
    setRole(authority.getRole());
    setDescription(authority.getDescription());
    setRemoval(authority.isRemoval());
  }


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

  @JsonValue
  @Override
  public String toString() {
    return getRole();
  }

}
