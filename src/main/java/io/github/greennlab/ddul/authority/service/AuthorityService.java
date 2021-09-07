package io.github.greennlab.ddul.authority.service;

import io.github.greennlab.ddul.authority.MappedTeamAuthority;
import io.github.greennlab.ddul.authority.dto.AuthorityInputDTO;
import io.github.greennlab.ddul.authority.dto.AuthorityOutputDTO;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AuthorityService {

  AuthorityOutputDTO getAuthority(Long id);

  Map<Long, Set<AuthorityOutputDTO>> getAuthoritiesByMenu();

  Map<Long, Set<AuthorityOutputDTO>> getAuthoritiesByTeam();

  AuthorityOutputDTO save(AuthorityInputDTO input);

  void saveAuthoritiesByTeam(List<MappedTeamAuthority> authority);
}
