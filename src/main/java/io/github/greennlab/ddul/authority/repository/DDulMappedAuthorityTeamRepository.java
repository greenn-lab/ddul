package io.github.greennlab.ddul.authority.repository;

import io.github.greennlab.ddul.authority.MappedAuthorityTeam;
import java.util.Set;
import org.springframework.data.repository.Repository;

public interface DDulMappedAuthorityTeamRepository extends Repository<MappedAuthorityTeam, Long> {

  Set<MappedAuthorityTeam> findAllByTeamId(Long userId);

  Set<MappedAuthorityTeam> saveAll(Iterable<MappedAuthorityTeam> entities);

}
