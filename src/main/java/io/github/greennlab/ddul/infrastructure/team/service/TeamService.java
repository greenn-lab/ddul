package io.github.greennlab.ddul.infrastructure.team.service;

import io.github.greennlab.ddul.infrastructure.team.Team;
import java.util.Set;

public interface TeamService {

  Team findAllHierarchicalTeam(Long id);

  Team findById(Long id);

  Team save(Team team);

  Set<Team> saveAll(Set<Team> teams);

}
