package com.github.greennlab.ddul.menu;

import com.github.greennlab.ddul.entity.EntityAuditor;
import com.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.URL;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Where(clause = "DELETED = 'N'")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Menu extends EntityAuditor {

  private static final long serialVersionUID = -8379883687091265040L;

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String name;

  private String uri;
  private Long upperId;

  @Column(name = "ORD")
  private int order;

  @Column(name = "DSC", scale = 1000)
  private String description;

  private String icon;
  private String classes;
  private String badge;
  private boolean inactive;
  private boolean use;

  @OneToMany(mappedBy = "upperId", fetch = FetchType.EAGER)
  @OrderBy("order asc")
  private List<Menu> children = new ArrayList<>();


  // -------------------------------------------------------
  // DTO
  // -------------------------------------------------------
  @Getter
  @Setter
  static class Dto {

    private Long id;

    @NotEmpty
    private String name;

    private String nameEn;

    @URL
    private String uri;

    private int order = 0;
    private String description;
    private String icon;
    private String classes;
    private String badge;
    private boolean inactive = false;
    private boolean use = true;

  }


  @Mapper
  public interface Mapping extends EntityDtoMapping<Menu, Dto> {

  }

}
