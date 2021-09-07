package io.github.greennlab.ddul.infrastructure.authority;

import static io.github.greennlab.ddul.entity.BaseEntity.NOT_REMOVAL;

import io.github.greennlab.ddul.entity.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "_MAP_USER_AUTHORITY")
@Where(clause = NOT_REMOVAL)
@Getter
@Setter
public class MappedUserAuthority extends BaseEntity {

  private static final long serialVersionUID = -6254442608026678057L;


  private Long userId;

  @ManyToOne
  @JoinColumn(name = "AUTHORITY_ID")
  private Authority authority;

}
