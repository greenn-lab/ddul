package io.github.greennlab.ddul.infrastructure.team.controller;

import static io.github.greennlab.ddul.infrastructure.team.dto.TeamDTO.mapped;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

import io.github.greennlab.ddul.infrastructure.team.Team;
import io.github.greennlab.ddul.infrastructure.team.dto.TeamDTO;
import io.github.greennlab.ddul.infrastructure.team.service.TeamService;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
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
  public TeamDTO getTeam(Long id) {
    return mapped.to(service.findAllHierarchicalTeam(id));
  }

  @PostMapping
  public TeamDTO save(@Valid @RequestBody TeamDTO team) {
    final Team saved = service.save(mapped.by(team));
    return mapped.to(saved);
  }

  @PostMapping("all")
  public Set<TeamDTO> saveAll(@Valid @RequestBody Set<TeamDTO> teams) {
    final Set<Team> collect = Optional.ofNullable(teams).orElse(emptySet()).stream()
        .map(mapped::by)
        .collect(toSet());

    final Set<Team> saved = service.saveAll(collect);
    return saved.stream()
        .map(mapped::to)
        .collect(toSet());
  }

}
