package io.github.greennlab.ddul.infrastructure.authority.controller;

import static io.github.greennlab.ddul.infrastructure.authority.dto.AuthorityOutputDTO.mapped;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

import io.github.greennlab.ddul.infrastructure.authority.Authority;
import io.github.greennlab.ddul.infrastructure.authority.AuthorizedUser;
import io.github.greennlab.ddul.infrastructure.authority.dto.AuthorityInputDTO;
import io.github.greennlab.ddul.infrastructure.authority.dto.AuthorityOutputDTO;
import io.github.greennlab.ddul.infrastructure.authority.service.AuthorityService;
import io.github.greennlab.ddul.infrastructure.user.annotation.LoggedIn;
import io.github.greennlab.ddul.infrastructure.user.service.UserService;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("DDulAuthorityController")
@RequestMapping("_authority")
@RequiredArgsConstructor
public class AuthorityController {

  private final AuthorityService service;

  private final UserService userService;


  @GetMapping
  public AuthorityOutputDTO getAuthority(Long id) {
    return mapped.toHierarchy(service.getAuthorityHierarchy(id));
  }

  @GetMapping("authorized")
  public Collection<GrantedAuthority> getAuthorized(@LoggedIn AuthorizedUser authorized) {
    return service.getAllAuthorized(authorized.getUser());
  }


  @GetMapping("authorized/{username}")
  public Collection<GrantedAuthority> getAuthorized(@PathVariable String username) {
    final AuthorizedUser user = (AuthorizedUser) userService.loadUserByUsername(username);

    return service.getAllAuthorized(user.getUser());
  }


  @PostMapping
  public AuthorityOutputDTO save(@Valid @RequestBody AuthorityInputDTO input) {
    final Authority saved = service.save(AuthorityInputDTO.mapped.by(input));
    return mapped.to(saved);
  }

  @GetMapping("menu")
  public Map<Long, Set<AuthorityOutputDTO>> getMenuAuthorities() {
    return convertEntityMapToDto(service.getAllMenuAuthorities());
  }

  @PostMapping("menu/{id}")
  public Set<AuthorityOutputDTO> saveMenuAuthorities(
      @PathVariable Long id,
      @RequestBody Set<AuthorityInputDTO> authorities) {
    return service.saveAllMappedMenuAuthorities(id, convertDtoToEntitySet(authorities)).stream()
        .map(mapped::to)
        .collect(toSet());
  }

  @GetMapping("team")
  public Map<Long, Set<AuthorityOutputDTO>> getAuthoritiesByTeam() {
    return convertEntityMapToDto(service.getAuthoritiesByTeams());
  }

  @PostMapping("team/{id}")
  public Set<AuthorityOutputDTO> saveTeamAuthorities(
      @PathVariable Long id,
      @RequestBody Set<AuthorityInputDTO> authorities) {
    return service.saveAllMappedTeamAuthorities(id, convertDtoToEntitySet(authorities)).stream()
        .map(mapped::to)
        .collect(toSet());
  }

  @GetMapping("user/{id}")
  public Set<AuthorityOutputDTO> getAuthoritiesByUser(
      @PathVariable Long id) {
    return service.getAuthoritiesByUserId(id).stream()
        .map(mapped::to)
        .collect(toSet());
  }

  @PostMapping("user/{id}")
  public Set<AuthorityOutputDTO> saveUserAuthorities(
      @PathVariable Long id,
      @RequestBody Set<AuthorityInputDTO> authorities) {
    return service.saveAllMappedUserAuthorities(id, convertDtoToEntitySet(authorities)).stream()
        .map(mapped::to)
        .collect(toSet());
  }


  private Set<Authority> convertDtoToEntitySet(Set<AuthorityInputDTO> authorities) {
    return Optional.ofNullable(authorities).orElse(emptySet())
        .stream()
        .map(AuthorityInputDTO.mapped::by)
        .collect(toSet());
  }

  private Map<Long, Set<AuthorityOutputDTO>> convertEntityMapToDto(
      Map<Long, Set<Authority>> entityMap) {

    Map<Long, Set<AuthorityOutputDTO>> result = new HashMap<>();

    entityMap.forEach((key, value) ->
        result.put(key, value.stream()
            .map(mapped::to)
            .collect(toSet())));

    return result;
  }

}
