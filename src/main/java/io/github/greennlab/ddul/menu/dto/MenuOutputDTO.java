package io.github.greennlab.ddul.menu.dto;

import io.github.greennlab.ddul.entity.JsonMap;
import io.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import io.github.greennlab.ddul.menu.Menu;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Getter
@Setter
public class MenuOutputDTO {

  @Mapper
  public interface MenuOutputMapper extends EntityDtoMapping<Menu, MenuOutputDTO> {

  }


  public static final MenuOutputMapper mapped = Mappers.getMapper(MenuOutputMapper.class);


  private Long id;
  private Long pid;
  private String name;
  private String nameAid;
  private String uri;
  private String description;
  private int order;
  private JsonMap props;
  private LocalDateTime opened;
  private List<MenuOutputDTO> children;
  private boolean removal;

}
