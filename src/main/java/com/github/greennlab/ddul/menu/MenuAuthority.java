package com.github.greennlab.ddul.menu;

import com.github.greennlab.ddul.entity.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "MENU_AUTHORITY")
@Where(clause = "REMOVAL = 'N'")
@Getter
@Setter
public class MenuAuthority extends BaseEntity {

  private static final long serialVersionUID = -7034907455336320332L;


  @Id
  @Generated
  private Long id;

  @ManyToOne
  @JoinColumn(name = "MENU_ID")
  private Menu menu;

//  @ManyToOne
//  @JoinColumn(name = "AUTHORITY_ID")
//  private Authority authority;

}
