package com.github.greennlab.ddul.menu;

import com.github.greennlab.ddul.entity.Auditor;
import com.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import java.time.LocalDateTime;
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
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Menu extends Auditor {

  private static final long serialVersionUID = -8379883687091265040L;


  @Id
  @GeneratedValue
  private Long id;

  private Long upperId;

  @Column(nullable = false)
  private String name;

  private String nameAid;
  private String uri;

  @Column(name = "DSC", scale = 1000)
  private String description;

  @Column(name = "ORD")
  private int order;

  private String badge;
  private String icon;

  @Column(name = "ATTR")
  private String attribute;

  private LocalDateTime opened;

  @OneToMany(mappedBy = "upperId", fetch = FetchType.EAGER)
  @OrderBy("order asc")
  private List<Menu> children = new ArrayList<>();


  // -------------------------------------------------------
  // Underling
  // -------------------------------------------------------
  @Mapper
  public interface Of extends EntityDtoMapping<Menu, Dto> {

    Of map = Mappers.getMapper(Of.class);

  }

  @Data
  public static class Dto {

    private Long id;
    private Long upperId;
    private String name;
    private String nameAid;
    private String uri;
    private String description;
    private int order;
    private String badge;
    private String icon;
    private String attr;
    private LocalDateTime opened;
    private List<Dto> children;
    private LocalDateTime deleted;

  }

}
