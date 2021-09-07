package io.github.greennlab.ddul.infrastructure.user.repository;

import io.github.greennlab.ddul.infrastructure.user.MappedUserTeam;
import java.util.Set;
import org.springframework.data.repository.Repository;

public interface DDulMappedUserTeamRepository extends Repository<MappedUserTeam, Long> {

  Set<MappedUserTeam> findAllByUserId(Long userId);

  MappedUserTeam save(MappedUserTeam mappedUserTeam);

}
