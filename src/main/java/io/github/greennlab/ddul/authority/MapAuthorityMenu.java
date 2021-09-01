package io.github.greennlab.ddul.authority;

import static io.github.greennlab.ddul.Application.DB_PREFIX;

import io.github.greennlab.ddul.entity.BaseEntity;
import io.github.greennlab.ddul.menu.Menu;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = DB_PREFIX + "MAP_AUTHORITY_MENU")
@Getter
@Setter
public class MapAuthorityMenu extends BaseEntity {

  private static final long serialVersionUID = -7034907455336320332L;


  @Id
  @Generated
  private Long id;

  @ManyToOne
  @JoinColumn(name = "MENU_ID")
  private Menu menu;

}
