package io.github.greennlab.ddul.authority.service;

import static io.github.greennlab.ddul.authority.dto.AuthorityOutputDTO.mapped;

import io.github.greennlab.ddul.authority.AuthorityHierarchy;
import io.github.greennlab.ddul.authority.MappedTeamAuthority;
import io.github.greennlab.ddul.authority.dto.AuthorityInputDTO;
import io.github.greennlab.ddul.authority.dto.AuthorityOutputDTO;
import io.github.greennlab.ddul.authority.repository.DDulAuthorityRepository;
import io.github.greennlab.ddul.authority.repository.DDulMappedMenuRepositoryAuthority;
import io.github.greennlab.ddul.authority.repository.DDulMappedTeamAuthorityRepository;
import io.github.greennlab.ddul.team.Team;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("DDulAuthorityService")
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

  private final DDulAuthorityRepository repository;

  private final DDulMappedMenuRepositoryAuthority menuRepository;

  private final DDulMappedTeamAuthorityRepository teamRepository;


  @Override
  public AuthorityOutputDTO getAuthority(Long id) {
    final AuthorityHierarchy authorityHierarchy = repository.findById(id)
        .orElseThrow(NoSuchElementException::new);

    return mapped.to(authorityHierarchy);
  }

  @Override
  public Map<Long, Set<AuthorityOutputDTO>> getMenuAuthorities() {
    final Map<Long, Set<AuthorityOutputDTO>> result = new HashMap<>();

    menuRepository.findAll().forEach(i -> {
      final Long id = i.getMenu().getId();
      final Set<AuthorityOutputDTO> roles = result.containsKey(id)
          ? result.get(id)
          : new HashSet<>();

      roles.add(mapped.to(new AuthorityHierarchy(i.getAuthority())));

      result.put(id, roles);
    });

    return result;
  }

  public Map<Long, Set<AuthorityOutputDTO>> getAuthoritiesByTeam() {
    final Map<Long, Set<AuthorityOutputDTO>> result = new HashMap<>();

    teamRepository.findAll().forEach(i -> {
      final Long id = i.getTeam().getId();
      final Set<AuthorityOutputDTO> roles = result.containsKey(id)
          ? result.get(id)
          : new HashSet<>();

      roles.add(mapped.to(new AuthorityHierarchy(i.getAuthority())));

      result.put(id, roles);
    });

    return result;
  }

  @Override
  public AuthorityOutputDTO save(AuthorityInputDTO input) {
    final AuthorityHierarchy saved = repository.save(AuthorityInputDTO.mapped.by(input));
    return mapped.to(saved);
  }

  @Transactional
  @Override
  public Set<MappedTeamAuthority> saveAuthoritiesByTeam(Set<MappedTeamAuthority> authorities) {
    // 이전 권한은 지워요.
    // 매핑테이블의 기본적인 방식이에요.
    authorities.stream()
        .map(MappedTeamAuthority::getTeam)
        .map(Team::getId)
        .distinct()
        .forEach(
            id -> teamRepository.findAllByTeamId(id).forEach(teamAuthority -> {
              teamAuthority.setRemoval(true);
              teamRepository.save(teamAuthority);
            })
        );

    return teamRepository.saveAll(
        authorities.stream()
            .filter(i -> null != i.getAuthority())
            .collect(Collectors.toSet())
    );
  }

}
