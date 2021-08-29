package com.github.greennlab.ddul.team.repository;

import com.github.greennlab.ddul.entity.AFewRepository;
import com.github.greennlab.ddul.team.Team;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TeamRepository extends AFewRepository<Team> {

  @Modifying
  @Query("UPDATE Team t SET t.removal = true WHERE t.id = :id")
  void deleteById(Long id);

}
