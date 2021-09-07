package io.github.greennlab.ddul.authority.repository;

import io.github.greennlab.ddul.authority.MappedTeamAuthority;
import java.util.Set;
import org.springframework.data.repository.Repository;

public interface DDulMappedTeamAuthorityRepository extends Repository<MappedTeamAuthority, Long> {

  Set<MappedTeamAuthority> findAll();

  Set<MappedTeamAuthority> findAllByTeamId(Long id);

  Set<MappedTeamAuthority> saveAll(Iterable<MappedTeamAuthority> entities);

}
