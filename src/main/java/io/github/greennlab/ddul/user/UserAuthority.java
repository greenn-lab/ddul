package io.github.greennlab.ddul.user;

import io.github.greennlab.ddul.authority.Authority;
import io.github.greennlab.ddul.entity.BaseEntity;
import io.github.greennlab.ddul.Application;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = Application.DB_PREFIX + "USER_AUTHORITY")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class UserAuthority extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -6254442608026678057L;

  private Long userId;

  @ManyToOne
  @JoinColumn(name = "AUTHORITY_ID")
  private Authority authority;

}
