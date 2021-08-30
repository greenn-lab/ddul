package com.github.greennlab.ddul.team;

import static com.github.greennlab.ddul.Application.DB_PREFIX;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = DB_PREFIX + "TEAM")
@Getter
@Setter
public class TeamHierarchy {

  private static final long serialVersionUID = -4457282553321580200L;


  @Id
  private Long id;

  private Long pid;
  private String name;

  @Column(name = "ORD")
  private Integer order;

  @OneToMany(mappedBy = "pid", fetch = FetchType.EAGER)
  @OrderBy("order asc")
  private List<TeamHierarchy> children = new ArrayList<>();

}
