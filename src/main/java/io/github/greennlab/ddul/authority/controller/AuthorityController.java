package io.github.greennlab.ddul.authority.controller;

import io.github.greennlab.ddul.authority.MappedTeamAuthority;
import io.github.greennlab.ddul.authority.dto.AuthorityInputDTO;
import io.github.greennlab.ddul.authority.dto.AuthorityOutputDTO;
import io.github.greennlab.ddul.authority.service.AuthorityService;
import java.util.Map;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("DDulAuthorityController")
@RequestMapping("_authority")
@RequiredArgsConstructor
public class AuthorityController {

  private final AuthorityService service;


  @GetMapping
  public AuthorityOutputDTO getAuthority(Long id) {
    return service.getAuthority(id);
  }

  @PostMapping
  public AuthorityOutputDTO save(@Valid @RequestBody AuthorityInputDTO input) {
    return service.save(input);
  }

  @GetMapping("menu")
  public Map<Long, Set<AuthorityOutputDTO>> getMenuAuthorities() {
    return service.getMenuAuthorities();
  }

  @GetMapping("team")
  public Map<Long, Set<AuthorityOutputDTO>> getAuthoritiesByTeam() {
    return service.getAuthoritiesByTeam();
  }

  @PostMapping("team")
  public Set<MappedTeamAuthority> save(@RequestBody Set<MappedTeamAuthority> authorities) {
    return service.saveAuthoritiesByTeam(authorities);
  }

}
