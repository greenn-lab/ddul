package io.github.greennlab.ddul.authority.service;

import static io.github.greennlab.ddul.authority.dto.AuthorityOutputDTO.mapped;

import io.github.greennlab.ddul.authority.Authority;
import io.github.greennlab.ddul.authority.dto.AuthorityInputDTO;
import io.github.greennlab.ddul.authority.dto.AuthorityOutputDTO;
import io.github.greennlab.ddul.authority.repository.DDulAuthorityRepository;
import io.github.greennlab.ddul.authority.repository.DDulMappedAuthorityMenuRepository;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("DDulAuthorityService")
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

  private final DDulAuthorityRepository repository;

  private final DDulMappedAuthorityMenuRepository mappedAuthorityMenuRepository;


  @Override
  public AuthorityOutputDTO getAuthority(Long id) {
    final Authority authority = repository.findById(id)
        .orElseThrow(NoSuchElementException::new);

    return mapped.to(authority);
  }

  @Override
  public Map<Long, Set<String>> getMenuRoles() {
    final Map<Long, Set<String>> result = new HashMap<>();

    mappedAuthorityMenuRepository.findAll().forEach(i -> {
      final Long id = i.getMenu().getId();
      final Set<String> roles = result.containsKey(id)
          ? result.get(id)
          : new HashSet<>();

      roles.add(i.getAuthority().getRole());

      result.put(id, roles);
    });

    return result;
  }

  @Override
  public AuthorityOutputDTO save(AuthorityInputDTO input) {
    final Authority saved = repository.save(AuthorityInputDTO.mapped.by(input));
    return mapped.to(saved);
  }

}
