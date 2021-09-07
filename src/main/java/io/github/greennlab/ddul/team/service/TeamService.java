package io.github.greennlab.ddul.team.service;

import io.github.greennlab.ddul.team.dto.TeamInputDTO;
import io.github.greennlab.ddul.team.dto.TeamOutputDTO;
import java.util.List;

public interface TeamService {

  TeamOutputDTO findAllHierarchicalTeam(Long id);

  TeamOutputDTO findById(Long id);

  TeamOutputDTO save(TeamInputDTO team);

  List<TeamOutputDTO> saveAll(List<TeamInputDTO> teams);

  void remove(Long id);

}
