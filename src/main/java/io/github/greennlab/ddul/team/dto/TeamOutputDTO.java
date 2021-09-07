package io.github.greennlab.ddul.team.dto;

import io.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import io.github.greennlab.ddul.team.Team;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Getter
@Setter
public class TeamOutputDTO extends TeamInputDTO {

  public static final TeamOutputMapper mapped = Mappers.getMapper(TeamOutputMapper.class);


  private List<TeamOutputDTO> children = new ArrayList<>();


  @Mapper
  public interface TeamOutputMapper extends EntityDtoMapping<Team, TeamOutputDTO> {

  }
}
