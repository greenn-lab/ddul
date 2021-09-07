package io.github.greennlab.ddul.infrastructure.team.repository;

import io.github.greennlab.ddul.infrastructure.team.TeamHierarchy;
import java.util.List;
import org.springframework.data.repository.Repository;

public interface DDulTeamHierarchyRepository extends Repository<TeamHierarchy, Long> {

  TeamHierarchy findById(Long id);

  List<TeamHierarchy> findAllByPidIsNullOrderByOrder();

}
