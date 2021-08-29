package com.github.greennlab.ddul.team.service;

import com.github.greennlab.ddul.team.Team;

public interface TeamService {

  Team findAllHierarchicalTeam(Long id);

  Team findById(Long id);

  Team save(Team team);

  void remove(Long id);

}
