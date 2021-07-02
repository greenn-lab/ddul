package com.github.greennlab.ddul.menu;

import com.github.greennlab.ddul.entity.Auditor;
import com.github.greennlab.ddul.authority.Authority;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Where(clause = "DELETED = 'N'")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class MenuAuthority extends Auditor {

  private static final long serialVersionUID = -7034907455336320332L;


  @Id
  @Generated
  private Long id;

  @ManyToOne
  @JoinColumn(name = "MENU_ID")
  private Menu menu;

  @ManyToOne
  @JoinColumn(name = "AUTHORITY_ID")
  private Authority authority;

}
