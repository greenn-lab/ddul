package io.github.greennlab.ddul.infrastructure.team;

import static io.github.greennlab.ddul.entity.BaseEntity.NOT_REMOVAL;

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
import org.hibernate.annotations.Where;

@Entity
@Table(name = "_TEAM")
@Where(clause = NOT_REMOVAL)
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

  private boolean removal;

  @OneToMany(mappedBy = "pid", fetch = FetchType.EAGER)
  @OrderBy("order asc")
  private List<TeamHierarchy> children = new ArrayList<>();

}
