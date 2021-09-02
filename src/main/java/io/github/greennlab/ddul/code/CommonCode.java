package io.github.greennlab.ddul.code;

import io.github.greennlab.ddul.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CODE")
@Getter
@Setter
public class CommonCode extends BaseEntity {

  private static final long serialVersionUID = -8379883687091265041L;


  @Column(name = "GRP_COD")
  private String group;

  @Column(name = "COD")
  private String code;

  @Column(name = "COD_NAME")
  private String name;

  @Column(name = "ORD")
  private int order;

  private boolean use;

}
