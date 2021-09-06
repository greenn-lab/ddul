package io.github.greennlab.ddul.authority;

import static io.github.greennlab.ddul.entity.BaseEntity.NOT_REMOVAL;

import io.github.greennlab.ddul.entity.BaseEntity;
import io.github.greennlab.ddul.menu.Menu;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "MAP_AUTHORITY_MENU")
@Where(clause = NOT_REMOVAL)
@Getter
@Setter
public class MapAuthorityMenu extends BaseEntity {

  private static final long serialVersionUID = -7034907455336320332L;


  @ManyToOne
  @JoinColumn(name = "AUTHORITY_ID", insertable = false, updatable = false)
  @Where(clause = NOT_REMOVAL)
  private Authority authority;

  @ManyToOne
  @JoinColumn(name = "MENU_ID", insertable = false, updatable = false)
  @Where(clause = NOT_REMOVAL)
  private Menu menu;

}
