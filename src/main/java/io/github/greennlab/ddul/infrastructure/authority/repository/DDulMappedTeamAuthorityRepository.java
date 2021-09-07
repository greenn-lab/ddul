package io.github.greennlab.ddul.infrastructure.authority.repository;

import io.github.greennlab.ddul.infrastructure.authority.MappedTeamAuthority;
import java.util.Collection;
import java.util.Set;
import org.springframework.data.repository.Repository;

public interface DDulMappedTeamAuthorityRepository extends Repository<MappedTeamAuthority, Long> {

  Set<MappedTeamAuthority> findAll();

  Set<MappedTeamAuthority> findAllByTeamId(Long teamId);

  Set<MappedTeamAuthority> findAllByTeamIdIn(Collection<Long> teamIds);

  MappedTeamAuthority save(MappedTeamAuthority entity);

  Set<MappedTeamAuthority> saveAll(Iterable<MappedTeamAuthority> entities);

}
