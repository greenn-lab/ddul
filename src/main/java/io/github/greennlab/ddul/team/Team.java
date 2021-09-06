package io.github.greennlab.ddul.team;

import io.github.greennlab.ddul.entity.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TEAM")
@NoArgsConstructor
@Getter
@Setter
public class Team extends BaseEntity {

  private static final long serialVersionUID = 397067324743390394L;


  private Long pid;
  private String name;
  private int order;

  @Transient
  private List<Team> children = new ArrayList<>();


  public Team(TeamHierarchy teamHierarchy) {
    setId(teamHierarchy.getId());
    this.pid = teamHierarchy.getPid();
    this.name = teamHierarchy.getName();
    this.order = teamHierarchy.getOrder();
  }

}
