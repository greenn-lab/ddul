package io.github.greennlab.ddul.menu;

import io.github.greennlab.ddul.entity.BaseEntity;
import io.github.greennlab.ddul.entity.JsonMap;
import io.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Entity
@Table(name = "MENU")
@Getter
@Setter
public class Menu extends BaseEntity {

  public static final MenuOf mapped = Mappers.getMapper(MenuOf.class);

  private static final long serialVersionUID = -8379883687091265040L;


  private Long pid;

  @Column(nullable = false)
  private String name;

  private String nameAid;
  private String uri;

  @Column(name = "DSC", scale = 1000)
  private String description;

  @Column(name = "ORD")
  private int order;

  @Type(type = JsonMap.TYPE)
  private JsonMap props;

  private LocalDateTime opened;

  @OneToMany(mappedBy = "pid", fetch = FetchType.EAGER)
  @OrderBy("order asc")
  private Set<Menu> children = new HashSet<>();


  // -------------------------------------------------------
  // Underling
  // -------------------------------------------------------
  @Mapper
  public interface MenuOf extends EntityDtoMapping<Menu, Dto> {

  }

  @Data
  public static class Dto {

    private Long id;
    private Long pid;
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
    private boolean removal;

  }

}
