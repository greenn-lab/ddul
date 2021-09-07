package io.github.greennlab.ddul.infrastructure.team.dto;

import io.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import io.github.greennlab.ddul.infrastructure.team.Team;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Getter
@Setter
public class TeamDTO {

  public static final TeamMapper mapped = Mappers.getMapper(TeamMapper.class);

  private Long id;
  private Long pid;

  @NotEmpty
  private String name;

  private int order;
  private boolean removal;

  private List<TeamDTO> children = new ArrayList<>();


  @Mapper
  public interface TeamMapper extends EntityDtoMapping<Team, TeamDTO> {

  }
}
