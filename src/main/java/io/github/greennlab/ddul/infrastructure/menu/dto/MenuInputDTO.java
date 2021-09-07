package io.github.greennlab.ddul.infrastructure.menu.dto;

import io.github.greennlab.ddul.entity.JsonMap;
import io.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import io.github.greennlab.ddul.infrastructure.menu.Menu;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Getter
@Setter
public class MenuInputDTO {

  public static final MenuInputMapper mapped = Mappers.getMapper(MenuInputMapper.class);


  private Long id;
  private Long pid;

  @NotEmpty
  private String name;

  private String nameAid;
  private String uri;
  private String description;
  private int order;
  private JsonMap props;
  private LocalDateTime opened;
  private List<MenuInputDTO> children;
  private boolean removal;


  @Mapper
  public interface MenuInputMapper extends EntityDtoMapping<Menu, MenuInputDTO> {

  }

}
