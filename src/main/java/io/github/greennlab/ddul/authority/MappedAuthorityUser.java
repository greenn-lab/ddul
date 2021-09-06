package io.github.greennlab.ddul.authority;

import io.github.greennlab.ddul.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MAP_AUTHORITY_USER")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class MappedAuthorityUser extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -6254442608026678057L;

  private Long userId;

  @ManyToOne
  @JoinColumn(name = "AUTHORITY_ID")
  private Authority authority;

}
