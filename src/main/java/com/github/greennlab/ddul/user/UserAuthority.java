package com.github.greennlab.ddul.user;

import com.github.greennlab.ddul.entity.EntityAuditor;
import com.github.greennlab.ddul.authority.Authority;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Where(clause = "DELETED = 'N'")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter

public class UserAuthority extends EntityAuditor implements Serializable {

  private static final long serialVersionUID = -6254442608026678057L;


  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  @JoinColumn(name = "USER_ID")
  private User user;

  @ManyToOne
  @JoinColumn(name = "AUTHORITY_ID")
  private Authority authority;

}
