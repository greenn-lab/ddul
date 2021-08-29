package com.github.greennlab.ddul.team.service;

import com.github.greennlab.ddul.team.Team;
import com.github.greennlab.ddul.team.TeamHierarchy;
import com.github.greennlab.ddul.team.repository.TeamHierarchyRepository;
import com.github.greennlab.ddul.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("DDulTeamService")
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

  private final TeamRepository repository;

  private final TeamHierarchyRepository hierarchyRepository;


  @Override
  public Team findAllHierarchicalTeam(Long id) {
    final TeamHierarchy root = hierarchyRepository.findById(id);

    if (null == root) {
      return null;
    }

    return transform(root);
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
