package io.github.greennlab.ddul.authority.service;

import io.github.greennlab.ddul.authority.dto.AuthorityInputDTO;
import io.github.greennlab.ddul.authority.dto.AuthorityOutputDTO;
import java.util.Map;
import java.util.Set;

public interface AuthorityService {

  AuthorityOutputDTO getAuthority(Long id);

  Map<Long, Set<String>> getMenuRoles();

  AuthorityOutputDTO save(AuthorityInputDTO input);
}
