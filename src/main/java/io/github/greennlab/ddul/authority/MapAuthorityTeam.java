package io.github.greennlab.ddul.authority;

import io.github.greennlab.ddul.entity.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MAP_AUTHORITY_TEAM")
@Getter
@Setter
public class MapAuthorityTeam extends BaseEntity {

  private static final long serialVersionUID = -7034907455336320332L;


  @ManyToOne
  @JoinColumn(name = "AUTHORITY_ID")
  private Authority authority;

  private Long teamId;

}
