package io.github.greennlab.ddul.team.controller;

import io.github.greennlab.ddul.team.dto.TeamInputDTO;
import io.github.greennlab.ddul.team.dto.TeamOutputDTO;
import io.github.greennlab.ddul.team.service.TeamService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("DDulTeamController")
@RequestMapping("_team")
@RequiredArgsConstructor
public class TeamController {

  private final TeamService service;


  @GetMapping
  public TeamOutputDTO getTeam(Long id) {
    return service.findAllHierarchicalTeam(id);
  }

  @PostMapping
  public TeamOutputDTO save(@RequestBody TeamInputDTO team) {
    return service.save(team);
  }

  @PostMapping("all")
  public List<TeamOutputDTO> saveAll(@RequestBody List<TeamInputDTO> teams) {
    return service.saveAll(teams);
  }

}
