package com.github.greennlab.ddul.user;

import com.github.greennlab.ddul.authority.Authority;
import com.github.greennlab.ddul.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "USER_AUTHORITY")
@Where(clause = "REMOVAL = 'N'")
@Getter
@Setter
@NoArgsConstructor
public class UserAuthority extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -6254442608026678057L;

  @ManyToOne
  @JoinColumn(name = "AUTHORITY_ID")
  private Authority authority;


  public UserAuthority(String role) {
    this.authority = new Authority(role);
  }

}
