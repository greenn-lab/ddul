package io.github.greennlab.ddul.infrastructure.user;

import static io.github.greennlab.ddul.entity.BaseEntity.NOT_REMOVAL;

import io.github.greennlab.ddul.entity.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "_MAP_USER_TEAM")
@Where(clause = NOT_REMOVAL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MappedUserTeam extends BaseEntity {

  private static final long serialVersionUID = 3404488225141093659L;


  private Long userId;

  private Long teamId;

}
