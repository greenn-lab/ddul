package io.github.greennlab.ddul.infrastructure.team.service;

import static java.util.stream.Collectors.toSet;

import io.github.greennlab.ddul.infrastructure.team.Team;
import io.github.greennlab.ddul.infrastructure.team.TeamHierarchy;
import io.github.greennlab.ddul.infrastructure.team.repository.DDulTeamHierarchyRepository;
import io.github.greennlab.ddul.infrastructure.team.repository.DDulTeamRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("DDulTeamService")
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

  private final DDulTeamRepository repository;

  private final DDulTeamHierarchyRepository hierarchyRepository;


  @Override
  public Team findAllHierarchicalTeam(Long id) {
    if (null == id) {
      return findAllIfIdIsNull();
    }

    final TeamHierarchy root = hierarchyRepository.findById(id);

    if (null == root) {
      return null;
    }

    return transform(root);
  }

  private Team findAllIfIdIsNull() {
    final Team root = new Team();
    root.setId(-0L);
    root.setName("전체");

    final List<TeamHierarchy> tops = hierarchyRepository.findAllByPidIsNullOrderByOrder();
    for (TeamHierarchy team : tops) {
      root.getChildren().add(transform(team));
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
  public Team findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  @Override
  public Team save(Team team) {
    return repository.save(team);
  }

  @Override
  public Set<Team> saveAll(Set<Team> teams) {
    return teams.stream()
        .map(this::save)
        .collect(toSet());
  }

}
