package io.github.greennlab.ddul.team.service;

import io.github.greennlab.ddul.team.Team;
import io.github.greennlab.ddul.team.TeamHierarchy;
import io.github.greennlab.ddul.team.repository.DDulTeamHierarchyRepository;
import io.github.greennlab.ddul.team.repository.DDulTeamRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("DDulTeamService")
@RequiredArgsConstructor
public class DDulTeamServiceImpl implements TeamService {

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
  public void remove(Long id) {
    repository.deleteById(id);
  }

}
