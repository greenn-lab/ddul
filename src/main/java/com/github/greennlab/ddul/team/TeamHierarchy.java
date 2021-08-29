package com.github.greennlab.ddul.team;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TEAM")
@Getter
@Setter
public class TeamHierarchy {

  private static final long serialVersionUID = -4457282553321580200L;


  @Id
  private Long id;

  private Long pid;
  private String name;

  @OneToMany(mappedBy = "pid", fetch = FetchType.EAGER)
  private List<TeamHierarchy> children = new ArrayList<>();

}
