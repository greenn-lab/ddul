package io.github.greennlab.ddul.authority.repository;

import io.github.greennlab.ddul.authority.MapAuthorityTeam;
import java.util.Set;
import org.springframework.data.repository.Repository;

public interface DDulMapAuthorityTeamRepository extends Repository<MapAuthorityTeam, Long> {

  Set<MapAuthorityTeam> findAllByTeamId(Long userId);

  Set<MapAuthorityTeam> saveAll(Iterable<MapAuthorityTeam> entities);

}
