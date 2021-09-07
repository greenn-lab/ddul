package io.github.greennlab.ddul.infrastructure.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.greennlab.ddul.entity.BaseEntity;
import io.github.greennlab.ddul.infrastructure.team.Team;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "_USER")
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity {

  private static final long serialVersionUID = -7382145646927293876L;


  private String username;

  @JsonIgnore
  private String password;

  private LocalDate passwordExpired;
  private String email;
  private String name;
  private boolean use;

  @OneToOne
  @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
  private Team team;

}
