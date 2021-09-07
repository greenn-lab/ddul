package io.github.greennlab.ddul.authority.service;

import static io.github.greennlab.ddul.authority.dto.AuthorityOutputDTO.mapped;

import io.github.greennlab.ddul.authority.AuthorityHierarchy;
import io.github.greennlab.ddul.authority.MappedTeamAuthority;
import io.github.greennlab.ddul.authority.dto.AuthorityInputDTO;
import io.github.greennlab.ddul.authority.dto.AuthorityOutputDTO;
import io.github.greennlab.ddul.authority.repository.DDulAuthorityRepository;
import io.github.greennlab.ddul.authority.repository.DDulMappedAuthorityMenuRepository;
import io.github.greennlab.ddul.authority.repository.DDulMappedTeamAuthorityRepository;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

  private final DDulMappedTeamAuthorityRepository mappedAuthorityTeamRepository;


  @Override
  public AuthorityOutputDTO getAuthority(Long id) {
    final AuthorityHierarchy authorityHierarchy = repository.findById(id)
        .orElseThrow(NoSuchElementException::new);

    return mapped.to(authorityHierarchy);
  }

  @Override
  public Map<Long, Set<AuthorityOutputDTO>> getAuthoritiesByMenu() {
    final Map<Long, Set<AuthorityOutputDTO>> result = new HashMap<>();

    mappedAuthorityMenuRepository.findAll().forEach(i -> {
      final Long id = i.getMenu().getId();
      final Set<AuthorityOutputDTO> roles = result.containsKey(id)
          ? result.get(id)
          : new HashSet<>();

      roles.add(mapped.to(i.getAuthority().toHierarchy()));

      result.put(id, roles);
    });

    return result;
  }

  public Map<Long, Set<AuthorityOutputDTO>> getAuthoritiesByTeam() {
    final Map<Long, Set<AuthorityOutputDTO>> result = new HashMap<>();

    mappedAuthorityTeamRepository.findAll().forEach(i -> {
      final Long id = i.getTeam().getId();
      final Set<AuthorityOutputDTO> roles = result.containsKey(id)
          ? result.get(id)
          : new HashSet<>();

      roles.add(mapped.to(i.getAuthority().toHierarchy()));

      result.put(id, roles);
    });

    return result;
  }

  @Override
  public AuthorityOutputDTO save(AuthorityInputDTO input) {
    final AuthorityHierarchy saved = repository.save(AuthorityInputDTO.mapped.by(input));
    return mapped.to(saved);
  }

  @Override
  public void saveAuthoritiesByTeam(List<MappedTeamAuthority> authorities) {
    mappedAuthorityMenuRepository.saveAll(authorities);
  }

}
