package io.github.greennlab.ddul.team.service;

import static java.util.stream.Collectors.toList;

import io.github.greennlab.ddul.team.Team;
import io.github.greennlab.ddul.team.TeamHierarchy;
import io.github.greennlab.ddul.team.dto.TeamInputDTO;
import io.github.greennlab.ddul.team.dto.TeamInputDTO.TeamInputMapper;
import io.github.greennlab.ddul.team.dto.TeamOutputDTO;
import io.github.greennlab.ddul.team.dto.TeamOutputDTO.TeamOutputMapper;
import io.github.greennlab.ddul.team.repository.DDulTeamHierarchyRepository;
import io.github.greennlab.ddul.team.repository.DDulTeamRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("DDulTeamService")
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

  private static final TeamOutputMapper outputMapped = TeamOutputDTO.mapped;

  private static final TeamInputMapper inputMapped = TeamInputDTO.mapped;


  private final DDulTeamRepository repository;

  private final DDulTeamHierarchyRepository hierarchyRepository;


  @Override
  public TeamOutputDTO findAllHierarchicalTeam(Long id) {
    if (null == id) {
      return findAllIfIdIsNull();
    }

    final TeamHierarchy root = hierarchyRepository.findById(id);

    if (null == root) {
      return null;
    }

    return outputMapped.to(transform(root));
  }

  private TeamOutputDTO findAllIfIdIsNull() {
    final TeamOutputDTO root = new TeamOutputDTO();
    root.setId(-0L);
    root.setName("전체");

    final List<TeamHierarchy> tops = hierarchyRepository.findAllByPidIsNullOrderByOrder();
    for (TeamHierarchy team : tops) {
      root.getChildren().add(outputMapped.to(transform(team)));
    }

    return root;
  }

  private Team transform(TeamHierarchy team) {
    final Team result = new Team(team);

    for (TeamHierarchy child : team.getChildren()) {
      result.getChildren().add(transform(child));
    }

    return result;
  }

  @Override
  public TeamOutputDTO findById(Long id) {
    return outputMapped.to(repository.findById(id).orElse(null));
  }

  @Transactional
  @Override
  public TeamOutputDTO save(TeamInputDTO team) {
    final Team saved = repository.save(inputMapped.by(team));
    return outputMapped.to(saved);
  }

  @Transactional
  @Override
  public List<TeamOutputDTO> saveAll(List<TeamInputDTO> teams) {
    return teams.stream().map(this::save).collect(toList());
  }

  @Transactional
  @Override
  public void remove(Long id) {
    repository.deleteById(id);
  }

}
