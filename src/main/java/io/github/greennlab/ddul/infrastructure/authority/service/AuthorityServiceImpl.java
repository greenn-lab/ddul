package io.github.greennlab.ddul.infrastructure.authority.service;

import static java.util.stream.Collectors.toSet;

import io.github.greennlab.ddul.infrastructure.authority.Authority;
import io.github.greennlab.ddul.infrastructure.authority.AuthorityHierarchy;
import io.github.greennlab.ddul.infrastructure.authority.MappedMenuAuthority;
import io.github.greennlab.ddul.infrastructure.authority.MappedTeamAuthority;
import io.github.greennlab.ddul.infrastructure.authority.MappedUserAuthority;
import io.github.greennlab.ddul.infrastructure.authority.repository.DDulAuthorityHierarchyRepository;
import io.github.greennlab.ddul.infrastructure.authority.repository.DDulAuthorityRepository;
import io.github.greennlab.ddul.infrastructure.authority.repository.DDulMappedMenuAuthorityRepository;
import io.github.greennlab.ddul.infrastructure.authority.repository.DDulMappedTeamAuthorityRepository;
import io.github.greennlab.ddul.infrastructure.authority.repository.DDulMappedUserAuthorityRepository;
import io.github.greennlab.ddul.infrastructure.user.MappedUserTeam;
import io.github.greennlab.ddul.infrastructure.user.User;
import io.github.greennlab.ddul.infrastructure.user.repository.DDulMappedUserTeamRepository;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service("DDulAuthorityService")
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

  private final DDulAuthorityRepository repository;

  private final DDulAuthorityHierarchyRepository hierarchyRepository;

  private final DDulMappedMenuAuthorityRepository mappedMenuRepository;

  private final DDulMappedTeamAuthorityRepository mappedTeamRepository;

  private final DDulMappedUserAuthorityRepository mappedUserRepository;

  private final DDulMappedUserTeamRepository mappedUserTeamRepository;


  @Override
  public AuthorityHierarchy getAuthorityHierarchy(Long id) {
    return hierarchyRepository.findById(id);
  }

  @Override
  public Set<GrantedAuthority> getAllAuthorized(@NonNull User user) {
    final Set<GrantedAuthority> authorities = new HashSet<>();

    getAuthoritiesByUserId(user.getId())
        .forEach(i -> addWithChildren(authorities, i));

    mappedTeamRepository.findAllByTeamIdIn(getAllTeamId(user))
        .forEach(i -> addWithChildren(authorities, i.getAuthority()));

    return authorities;
  }

  private Set<Long> getAllTeamId(User user) {
    final Set<Long> userTeamIds = new HashSet<>();
    Optional.ofNullable(user.getTeam()).ifPresent(i -> userTeamIds.add(i.getId()));

    mappedUserTeamRepository.findAllByUserId(user.getId()).stream()
        .map(MappedUserTeam::getTeamId)
        .forEach(userTeamIds::add);
    return userTeamIds;
  }

  private void addWithChildren(Set<GrantedAuthority> authorities, Authority authority) {
    final AuthorityHierarchy authorityHierarchy = hierarchyRepository.findById(authority.getId());
    if (null != authorityHierarchy) {
      authorities.add(authorityHierarchy);
      authorities.addAll(AuthorityHierarchy.spreadAll(authorityHierarchy.getChildren()));
    }
  }


  @Override
  public Map<Long, Set<Authority>> getAllMenuAuthorities() {
    final Map<Long, Set<Authority>> result = new HashMap<>();

    mappedMenuRepository.findAll().forEach(mapped -> {
      final Long menuId = mapped.getMenuId();
      final Set<Authority> roles = result.containsKey(menuId)
          ? result.get(menuId)
          : new HashSet<>();

      roles.add(mapped.getAuthority());
      result.put(menuId, roles);
    });

    return result;
  }

  public Map<Long, Set<Authority>> getAuthoritiesByTeams() {
    final Map<Long, Set<Authority>> result = new HashMap<>();

    mappedTeamRepository.findAll().forEach(mapped -> {
      final Long teamId = mapped.getTeamId();
      final Set<Authority> roles = result.containsKey(teamId)
          ? result.get(teamId)
          : new HashSet<>();

      roles.add(mapped.getAuthority());
      result.put(teamId, roles);
    });

    return result;
  }

  @Override
  public Set<Authority> getAuthoritiesByUserId(Long userId) {
    return mappedUserRepository.findAllByUserId(userId).stream()
        .map(MappedUserAuthority::getAuthority)
        .collect(toSet());
  }

  @Override
  public Authority save(Authority authority) {
    return repository.save(authority);
  }

  @Override
  public Set<Authority> saveAllMappedUserAuthorities(Long userId, Set<Authority> authorities) {
    // 이전 권한은 지워요.
    // 매핑테이블의 기본적인 전략이에요.
    final Set<MappedUserAuthority> beforeMappedUsers = mappedUserRepository.findAllByUserId(userId);
    for (MappedUserAuthority mappedUser : beforeMappedUsers) {
      mappedUser.setRemoval(true);
    }

    mappedUserRepository.saveAll(beforeMappedUsers);

    final Set<MappedUserAuthority> mappedUsers = authorities.stream()
        .map(i -> {
          final MappedUserAuthority mapped = new MappedUserAuthority();
          mapped.setUserId(userId);
          mapped.setAuthority(i);
          return mapped;
        })
        .collect(toSet());

    return mappedUserRepository.saveAll(mappedUsers)
        .stream().map(MappedUserAuthority::getAuthority)
        .collect(toSet());
  }

  @Override
  public Set<Authority> saveAllMappedTeamAuthorities(Long teamId, Set<Authority> authorities) {
    // 이전 권한은 지워요.
    // 매핑테이블의 기본적인 전략이에요.
    final Set<MappedTeamAuthority> beforeMappedTeams = mappedTeamRepository.findAllByTeamId(teamId);
    for (MappedTeamAuthority mappedTeam : beforeMappedTeams) {
      mappedTeam.setRemoval(true);
    }

    mappedTeamRepository.saveAll(beforeMappedTeams);

    final Set<MappedTeamAuthority> mappedTeams = authorities.stream()
        .map(i -> {
          final MappedTeamAuthority mapped = new MappedTeamAuthority();
          mapped.setTeamId(teamId);
          mapped.setAuthority(i);
          return mapped;
        })
        .collect(toSet());

    return mappedTeamRepository.saveAll(mappedTeams)
        .stream().map(MappedTeamAuthority::getAuthority)
        .collect(toSet());
  }

  @Override
  public Set<Authority> saveAllMappedMenuAuthorities(Long menuId, Set<Authority> authorities) {
    // 이전 권한은 지워요.
    // 매핑테이블의 기본적인 전략이에요.
    final Set<MappedMenuAuthority> beforeMappedMenus = mappedMenuRepository.findAllByMenuId(menuId);
    for (MappedMenuAuthority mappedMenu : beforeMappedMenus) {
      mappedMenu.setRemoval(true);
    }

    mappedMenuRepository.saveAll(beforeMappedMenus);

    final Set<MappedMenuAuthority> mappedMenus = authorities.stream()
        .map(i -> {
          final MappedMenuAuthority mapped = new MappedMenuAuthority();
          mapped.setMenuId(menuId);
          mapped.setAuthority(i);
          return mapped;
        })
        .collect(toSet());

    return mappedMenuRepository.saveAll(mappedMenus)
        .stream().map(MappedMenuAuthority::getAuthority)
        .collect(toSet());
  }

}
