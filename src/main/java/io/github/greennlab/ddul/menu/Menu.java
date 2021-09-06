package io.github.greennlab.ddul.menu;

import io.github.greennlab.ddul.entity.BaseEntity;
import io.github.greennlab.ddul.entity.JsonMap;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "MENU")
@Getter
@Setter
public class Menu extends BaseEntity {

  private static final long serialVersionUID = -8379883687091265040L;


  private Long pid;

  @Column(nullable = false)
  private String name;

  private String nameAid;
  private String uri;

  @Column(name = "DSC", scale = 1000)
  private String description;

  @Column(name = "ORD")
  private int order;

  @Type(type = JsonMap.TYPE)
  private JsonMap props;

  private LocalDateTime opened;

  @OneToMany(mappedBy = "pid", fetch = FetchType.EAGER)
  @OrderBy("order asc")
  private Set<Menu> children = new HashSet<>();


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Menu menu = (Menu) o;
    return null != getId() && getId().equals(menu.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }

}
