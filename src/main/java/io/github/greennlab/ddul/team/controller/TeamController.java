package io.github.greennlab.ddul.team.controller;

import io.github.greennlab.ddul.team.Team;
import io.github.greennlab.ddul.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("_team")
@RequiredArgsConstructor
public class TeamController {

  private final TeamService service;


  @GetMapping
  public Team getTeam(Long id) {
    return service.findAllHierarchicalTeam(id);
  }

}
