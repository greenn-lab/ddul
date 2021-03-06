package io.github.greennlab.ddul.infrastructure.team;

import io.github.greennlab.ddul.entity.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "_TEAM")
@NoArgsConstructor
@Getter
@Setter
public class Team extends BaseEntity {

  private static final long serialVersionUID = 397067324743390394L;


  private Long pid;
  private String name;

  @Column(name = "ORD")
  private int order;

  @Transient
  private List<Team> children = new ArrayList<>();


  public Team(TeamHierarchy teamHierarchy) {
    setId(teamHierarchy.getId());
    this.pid = teamHierarchy.getPid();
    this.name = teamHierarchy.getName();
    this.order = teamHierarchy.getOrder();
  }

  @Override
  public String toString() {
    return name;
  }
}
