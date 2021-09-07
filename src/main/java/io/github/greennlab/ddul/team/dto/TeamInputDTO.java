package io.github.greennlab.ddul.team.dto;

import io.github.greennlab.ddul.mapstruct.EntityDtoMapping;
import io.github.greennlab.ddul.team.Team;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Getter
@Setter
public class TeamInputDTO {

  public static final TeamInputMapper mapped = Mappers.getMapper(TeamInputMapper.class);


  private Long id;
  private Long pid;

  @NotEmpty
  private String name;

  private int order;


  @Mapper
  public interface TeamInputMapper extends EntityDtoMapping<Team, TeamInputDTO> {

  }
}
