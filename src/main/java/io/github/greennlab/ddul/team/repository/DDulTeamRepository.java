package io.github.greennlab.ddul.team.repository;

import io.github.greennlab.ddul.entity.AFewRepository;
import io.github.greennlab.ddul.team.Team;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DDulTeamRepository extends AFewRepository<Team> {

  @Modifying
  @Query("UPDATE Team t SET t.removal = true WHERE t.id = :id")
  void deleteById(Long id);

}
